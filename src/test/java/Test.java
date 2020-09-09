import com.caidanmao.utils.antispam.yidun.api.YiDun;
import com.caidanmao.utils.antispam.yidun.model.YiDunResult;

import java.io.IOException;

/**
 * @author ws
 * @date 2020/9/7
 */
public class Test {

    @org.junit.Test
    public void testImage() {
        try {
            YiDunResult result = new YiDun.Builder().
                    image("https://nos.netease.com/yidun/2-0-0-a6133509763d4d6eac881a58f1791976.jpg").
                    sync(true).build().
                    check();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testText(){
        try {
            YiDunResult result = new YiDun.Builder().
                    text("十里山路不换肩").
                    sync(true).build().
                    check();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @org.junit.Test
    public void testVideo(){
        try {
            YiDunResult result = new YiDun.Builder().
                    video("https://v-cdn.zjol.com.cn/280443.mp4").
                    sync(true).build().
                    check();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
