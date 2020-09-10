package com.caidanmao.utils.antispam.yidun.api;

import com.caidanmao.utils.antispam.yidun.check.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ws
 * @date 2020/9/8
 */
public class YiDun {


    private static final int FIXED_NUMBER_OF_THREADS = 20;
    private ExecutorService executorService;

    //线程安全的
    //类初始化时，立即加载这个对象
    private static YiDun instance;

    private YiDun() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(FIXED_NUMBER_OF_THREADS);
        }
    }

    // 获取单例的线程池对象
    public static YiDun getInstance() {
        if (instance == null) {
            synchronized (YiDun.class) {
                if (instance == null) {
                    instance = new YiDun();
                }
            }
        }
        return instance;
    }


    public YiDunCheck imageSync(String url) throws IOException {
        return image(url, true, null);
    }

    public YiDunCheck image(String url, boolean sync, AfterCheck afterCheck) throws IOException {
        ImageCheck imageCheck = new ImageCheck(url);
        this.execute(imageCheck, sync, afterCheck);
        return imageCheck;
    }


    public YiDunCheck textSync(String content) throws IOException {
        return text(content, true, null);
    }

    public YiDunCheck text(String content, boolean sync, AfterCheck afterCheck) throws IOException {
        TextCheck textCheck = new TextCheck(content);
        this.execute(textCheck, sync, afterCheck);
        return textCheck;
    }

    public YiDunCheck videoSync(String url) throws IOException {
        return video(url, true, null);
    }

    public YiDunCheck video(String url, boolean sync, AfterCheck afterCheck) throws IOException {
        VideoCheck videoCheck = new VideoCheck(url);
        this.execute(videoCheck, sync, afterCheck);
        return videoCheck;
    }

    private void execute(YiDunCheck check, boolean sync, AfterCheck afterCheck) throws IOException {
        if (sync) {
            check.check();
        } else {
            executorService.execute(() -> {
                try {
                    check.check();
                    afterCheck.execute(check.getResult());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
