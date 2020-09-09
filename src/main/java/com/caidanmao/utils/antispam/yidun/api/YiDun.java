package com.caidanmao.utils.antispam.yidun.api;

import com.caidanmao.utils.antispam.yidun.check.ImageCheck;
import com.caidanmao.utils.antispam.yidun.check.TextCheck;
import com.caidanmao.utils.antispam.yidun.check.VideoCheck;
import com.caidanmao.utils.antispam.yidun.check.YiDunCheck;
import com.caidanmao.utils.antispam.yidun.model.YiDunResult;

import java.io.IOException;

/**
 * @author ws
 * @date 2020/9/8
 */
public class YiDun {

    YiDunCheck yiDunCheck;

    /**
     * 默认同步
     */
    boolean sync = true;

    private YiDun(Builder builder) {
        this.yiDunCheck = builder.yiDunCheck;
        this.sync = builder.sync;
    }

    public YiDunResult check() throws IOException {
        yiDunCheck.setSync(sync);
        return yiDunCheck.check();
    }

    public static class Builder {

        YiDunCheck yiDunCheck;

        boolean sync = true;

        public Builder() {
        }

        public Builder image(String url) {
            this.yiDunCheck = new ImageCheck(url);
            return this;
        }
        public Builder text(String content) {
            this.yiDunCheck = new TextCheck(content);
            return this;
        }

        public Builder video(String url) {
            this.yiDunCheck = new VideoCheck(url);
            return this;
        }

        public Builder sync(boolean sync) {
            this.sync = sync;
            return this;
        }

        public YiDun build() {
            return new YiDun(this);
        }
    }

}
