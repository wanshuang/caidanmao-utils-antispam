package com.caidanmao.utils.antispam.yidun.check;

import com.caidanmao.utils.antispam.yidun.model.YiDunResult;
import com.google.common.collect.Maps;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author ws
 * @date 2020/9/8
 */
abstract class AbstractYiDunCheck implements YiDunCheck {

    /**
     * 产品密钥ID，产品标识
     */
    final String SECRETID = "9b4197f160c2070b6415e39ff56dbc4f";
    /**
     * 产品私有密钥，服务端生成签名信息使用，请严格保管，避免泄露
     */
    final String SECRETKEY = "3191937eb8f3202af17d553eeec7b685";

    /**
     * 返回成功码
     */
    final int CODE_SUCCESS = 200;

    /**
     * 同步器 判断使用 execute or enqueue
     */
    boolean sync = true;

    YiDunResult result;

    /**
     * 实例化HttpClient，发送http请求使用，可根据需要自行调参
     */
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)//设置连接超时时间
            .readTimeout(10000, TimeUnit.MILLISECONDS)//设置读取超时时间
            .build();

    Map<String, String> getParamsInit() {
        Map<String, String> params = Maps.newHashMap();
        // 1.设置公共参数
        params.put("secretId", SECRETID);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("nonce", String.valueOf(new Random().nextInt(10000)));
        return params;
    }

    /**
     * 生成签名信息
     *
     * @param params 接口请求参数名和参数值map，不包括signature参数名
     * @return
     */
    String genSignature(Map<String, String> params) throws UnsupportedEncodingException {
        // 1. 参数名按照ASCII码表升序排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 2. 按照排序拼接参数名与参数值
        StringBuffer paramBuffer = new StringBuffer();
        for (String key : keys) {
            paramBuffer.append(key).append(params.get(key) == null ? "" : params.get(key));
        }
        // 3. 将secretKey拼接到最后
        paramBuffer.append(SECRETKEY);

        // 4. MD5是128位长度的摘要算法，用16进制表示，一个十六进制的字符能表示4个位，所以签名后的字符串长度固定为32个十六进制字符。
        return DigestUtils.md5Hex(paramBuffer.toString().getBytes("UTF-8"));
    }

    /**
     * okhttp 同步请求器
     *
     * @return
     */
    void execute(Map<String, String> params, String api) throws IOException {
        Response response = okHttpClient.newCall(createRequest(params, api)).execute();
        setResult(response.body().string());
    }

    /**
     * okhttp 异步请求器
     *
     * @return
     */
    void enqueue(Map<String, String> params, String api){
        okHttpClient.newCall(createRequest(params, api)).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println(e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                setResult(response.body().string());
            }
        });
    };

    /**
     * 创建请求
     * @param params
     * @param api
     * @return
     */
    Request createRequest(Map<String, String> params, String api) {
        MultipartBody.Builder requestBuilder = new MultipartBody.Builder();

        // 状态请求参数
        for (Map.Entry<String, String> entry : params.entrySet()) {
            requestBuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }

        Request request = new Request.Builder()
                .url(api)
                .post(requestBuilder.build())
                .build();
        return request;
    }

    abstract void setResult(String responseJson);

    @Override
    public YiDunResult getResult() {
        return result;
    }

}
