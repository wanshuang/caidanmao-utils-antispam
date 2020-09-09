/*
 * @(#) ImageCheckAPIDemo.java 2016年3月15日
 *
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package com.caidanmao.utils.antispam.yidun.check;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caidanmao.utils.antispam.yidun.model.LabelInfo;
import com.caidanmao.utils.antispam.yidun.model.YiDunImageResult;
import com.caidanmao.utils.antispam.yidun.model.YiDunResult;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 图片策略
 * https://support.dun.163.com/documents/2018041902?docId=424387300808773632
 *
 * @author ws
 * @date 2020/9/8
 */
public class ImageCheck extends AbstractYiDunCheck {

    String url;

    public ImageCheck(String url) {
        this.url = url;
    }

    /**
     * 业务ID，易盾根据产品业务特点分配
     */
    private final String BUSINESSID = "8f6c9945041985b01bd857b579a7e8c7";
    /**
     * 易盾反垃圾云服务图片在线检测接口地址
     */
    private final String API_URL = "http://as.dun.163.com/v4/image/check";

    @Override
    public void check() throws IOException {
        Map<String, String> params = getParamsInit();

        params.put("businessId", BUSINESSID);
        params.put("version", "v4");
        JSONArray jsonArray = new JSONArray();
        // 传图片url进行检测，name结构产品自行设计，用于唯一定位该图片数据
        JSONObject image = new JSONObject();
        image.put("name", url);
        image.put("type", 1);
        // 主动回调地址url,如果设置了则走主动回调逻辑
        image.put("data", url);
        jsonArray.add(image);

        params.put("images", jsonArray.toJSONString());

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
        YiDunImageResult result = new YiDunImageResult();
        if (code == CODE_SUCCESS) {
            JSONArray antispamArray = (JSONArray) jsonObject.get("antispam");
            if (antispamArray != null && !antispamArray.isEmpty()) {
                for (Iterator<Object> iterator = antispamArray.iterator(); iterator.hasNext(); ) {
                    JSONObject antispam = (JSONObject) iterator.next();
                    result = JSONObject.parseObject(antispam.toJSONString(), YiDunImageResult.class);
                    JSONObject details = antispam.getJSONObject("details");
                    if (details != null && !details.isEmpty()) {
                        //命中内容
                        JSONArray hints = details.getJSONArray("hint");
//                        result.setHint(hints.toString());
                    }
                    JSONArray labels = antispam.getJSONArray("labels");
                    List<LabelInfo> ls = Lists.newArrayList();
                    for (Iterator<Object> labelsIterator = labels.iterator(); labelsIterator.hasNext(); ) {
                        JSONObject label = (JSONObject) labelsIterator.next();
                        LabelInfo info = LabelInfo.builder().label(YiDunResult.Label.get(label.getInteger("label"))).level(label.getInteger("level")).rate(label.getBigDecimal("rate")).build();
                        ls.add(info);
                    }
                    result.setLabelInfoList(ls);
                }
            }
        }
        result.setCode(code);
        result.setMsg(msg);
        this.result = result;
    }
}
