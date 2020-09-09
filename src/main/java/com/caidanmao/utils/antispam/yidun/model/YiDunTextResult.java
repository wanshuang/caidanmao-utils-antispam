package com.caidanmao.utils.antispam.yidun.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author ws
 * @date 2020/9/8
 */
@Data
@ToString(callSuper = true)
public class YiDunTextResult extends YiDunResult {

    /**
     * 文本策略　命中信息
     */
    String hint;
}
