package com.caidanmao.utils.antispam.yidun.check;

import com.caidanmao.utils.antispam.yidun.model.YiDunResult;

/**
 * @author ws
 * @date 2020/9/10
 */
@FunctionalInterface
public interface AfterCheck {

    /**
     * 异步执行内容
     * @param result
     */
    void execute(YiDunResult result);

}
