package com.caidanmao.utils.antispam.yidun.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author ws
 * @date 2020/9/8
 */
@Data
@ToString(callSuper = true)
public class YiDunImageResult extends YiDunResult {

    /**
     * 标签列表
     */
    List<LabelInfo> labelInfoList;


    /**
     * 图片名称(或图片标识)
     */
    String name;

}
