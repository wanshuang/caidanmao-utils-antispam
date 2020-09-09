package com.caidanmao.utils.antispam.yidun.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ws
 * @date 2020/9/8
 */
@Data
@Builder
public class LabelInfo {

    YiDunResult.Label label;
    Integer level;
    BigDecimal rate;
}
