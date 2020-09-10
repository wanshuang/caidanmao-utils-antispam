import com.caidanmao.utils.antispam.yidun.api.YiDun;
import com.caidanmao.utils.antispam.yidun.check.TextCheck;
import com.caidanmao.utils.antispam.yidun.model.YiDunResult;
import com.caidanmao.utils.antispam.yidun.model.YiDunTextResult;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author ws
 * @date 2020/9/7
 */
public class Test {

    @org.junit.Test
    public void testImageSync() {
        try {
            YiDun yiDun = YiDun.getInstance();
            YiDunResult result = yiDun.imageSync("https://nos.netease.com/yidun/2-0-0-a6133509763d4d6eac881a58f1791976.jpg").getResult();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testTextSync() {
        try {
            YiDun yiDun = YiDun.getInstance();
            YiDunResult result = yiDun.textSync("十里山路不换肩").getResult();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testVideoSync() {
        try {
            YiDun yiDun = YiDun.getInstance();
            YiDunResult result = yiDun.videoSync("https://v-cdn.zjol.com.cn/280443.mp4").getResult();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @org.junit.Test
    public void testText() {
        try {
            YiDun yiDun = YiDun.getInstance();
            yiDun.text("十里山路不换肩", false,
                    result -> {
                        YiDunTextResult textResult = (YiDunTextResult) result;
                        System.out.println("aftercheck->"+textResult.toString());
                    }).getResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
