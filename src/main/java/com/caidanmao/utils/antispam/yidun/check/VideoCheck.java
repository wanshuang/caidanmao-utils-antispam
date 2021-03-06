package com.caidanmao.utils.antispam.yidun.check;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.caidanmao.utils.antispam.yidun.model.YiDunVideoResult;

import java.io.IOException;
import java.util.Map;

/**
 * 视频策略
 * https://support.dun.163.com/documents/2018041901?docId=424375611814748160
 *
 * @author ws
 * @date 2020/9/8
 */
public class VideoCheck extends AbstractYiDunCheck {

    String url;

    public VideoCheck(String url) {
        this.url = url;
    }

    /**
     * 业务ID，易盾根据产品业务特点分配
     */
    private final String BUSINESSID = "655de33f52d8cd8843bc8e2940c177da";
    /**
     * 易盾反垃圾云服务图片在线检测接口地址
     */
    private final String API_URL = "http://as.dun.163.com/v3/video/submit";

    @Override
    public void check() throws IOException {

        Map<String, String> params = getParamsInit();

        params.put("businessId", BUSINESSID);
        params.put("version", "v3");
        params.put("dataId", System.currentTimeMillis() + "");
        params.put("url", url);

        //生成签名信息
        String signature = genSignature(params);
        params.put("signature", signature);

        if (sync) {
            //同步请求
            execute(params, API_URL);
        } else {
            enqueue(params, API_URL);
        }
    }

    @Override
    void setResult(String responseJson) {
        JSONObject jsonObject = JSON.parseObject(responseJson);
        int code = jsonObject.getInteger("code");
        String msg = jsonObject.getString("msg");
        YiDunVideoResult result = new YiDunVideoResult();
        if (code == CODE_SUCCESS) {
            JSONObject responseResult = jsonObject.getJSONObject("result");
            if (responseResult != null && !responseResult.isEmpty()) {
                result = JSONObject.parseObject(responseResult.toJSONString(), YiDunVideoResult.class);
            }
        }
        result.setCode(code);
        result.setMsg(msg);
        this.result = result;
    }
}
