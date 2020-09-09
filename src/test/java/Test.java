import com.caidanmao.utils.antispam.yidun.api.YiDun;
import com.caidanmao.utils.antispam.yidun.check.YiDunCheck;
import com.caidanmao.utils.antispam.yidun.model.YiDunResult;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ws
 * @date 2020/9/7
 */
public class Test {

    @org.junit.Test
    public void testImage() {
        try {
            YiDun yiDun = YiDun.getInstance();
            YiDunResult result = yiDun.image("https://nos.netease.com/yidun/2-0-0-a6133509763d4d6eac881a58f1791976.jpg", true).getResult();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testText() {
        try {
            YiDun yiDun = YiDun.getInstance();
            YiDunResult result = yiDun.text("十里山路不换肩", true).getResult();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testTextNotAsync() {
        List<YiDunCheck> list = Lists.newArrayList();
        try {
            YiDun yiDun = YiDun.getInstance();
            for (int i = 0; i < 10; i++) {
                YiDunCheck check = yiDun.text("十里山路不换肩" + i, false);
                list.add(check);
                System.out.println(check.getResult());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        list.forEach(check -> {
            System.out.println(check.getResult().toString());
        });

    }

    @org.junit.Test
    public void testVideo() {
        try {
            YiDun yiDun = YiDun.getInstance();
            YiDunResult result = yiDun.video("https://v-cdn.zjol.com.cn/280443.mp4", true).getResult();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
