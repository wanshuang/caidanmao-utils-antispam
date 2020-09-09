package com.caidanmao.utils.antispam.yidun.check;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caidanmao.utils.antispam.yidun.model.YiDunTextResult;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * 文本策略
 * https://support.dun.163.com/documents/2018041901?docId=424375611814748160
 *
 * @author ws
 * @date 2020/9/8
 */
public class TextCheck extends AbstractYiDunCheck {

    String content;

    public TextCheck(String content) {
        this.content = content;
    }


    /**
     * 业务ID，易盾根据产品业务特点分配
     */
    private final String BUSINESSID = "f17b5102572131294cf665aa46400331";
    /**
     * 易盾反垃圾云服务图片在线检测接口地址
     */
    private final String API_URL = "http://as.dun.163.com/v3/text/check";

    @Override
    public void check() throws IOException {

        Map<String, String> params = getParamsInit();

        params.put("businessId", BUSINESSID);
        params.put("version", "v3.1");

        // 2.设置私有参数
        params.put("dataId", System.currentTimeMillis() + "");
        params.put("content", content);

        // 生成签名信息
        String signature = genSignature(params);
        params.put("signature", signature);

        if (sync) {
            //同步post
            execute(params, API_URL);
        } else {
            //异步post
            enqueue(params, API_URL);
        }
    }

    @Override
    void setResult(String responseJson) {
        JSONObject jsonObject = JSON.parseObject(responseJson);
        int code = jsonObject.getInteger("code");
        String msg = jsonObject.getString("msg");
        YiDunTextResult result = new YiDunTextResult();
        if (code == CODE_SUCCESS) {
            JSONObject responseResult = jsonObject.getJSONObject("result");
            if (responseResult != null && !responseResult.isEmpty()) {
                result = JSONObject.parseObject(responseResult.toJSONString(), YiDunTextResult.class);
                //详情
                JSONArray labels = responseResult.getJSONArray("labels");
                for (Iterator<Object> iterator = labels.iterator(); iterator.hasNext(); ) {
                    JSONObject lab = (JSONObject) iterator.next();
                    JSONObject details = lab.getJSONObject("details");
                    if (details != null && !details.isEmpty()) {
                        //命中内容
                        JSONArray hints = details.getJSONArray("hint");
                        result.setHint(hints.toString());
                    }
                    result.setLabel(lab.getIntValue("label"));
                }

            }
        }
        result.setCode(code);
        result.setMsg(msg);
        this.result = result;
    }
}
