import com.hust.hui.alarm.plugin.feishu.util.FeishuPublisher;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by @author yihui in 12:06 20/4/21.
 */
public class TestFeishuPublisher {

    @Test
    public void testPublish() throws IOException {
        String ans =
                FeishuPublisher.doPost("飞书", "测试报警", "");
        System.out.println(ans);
    }

}
