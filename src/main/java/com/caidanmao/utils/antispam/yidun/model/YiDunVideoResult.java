package com.caidanmao.utils.antispam.yidun.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author ws
 * @date 2020/9/8
 */
@Data
@ToString(callSuper = true)
public class YiDunVideoResult extends YiDunResult {

    /**
     * 视频策略　缓冲池排队待处理数据量
     */
    Long dealingCount;
}
