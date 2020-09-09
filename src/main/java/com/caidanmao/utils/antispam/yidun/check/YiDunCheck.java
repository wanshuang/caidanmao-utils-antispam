package com.caidanmao.utils.antispam.yidun.check;

import com.caidanmao.utils.antispam.yidun.model.YiDunResult;

import java.io.IOException;

/**
 * @author ws
 * @date 2020/9/8
 */
public interface YiDunCheck {

    void check() throws IOException;

    YiDunResult getResult();
}
