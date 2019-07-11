import com.monkeydp.demo.thrift.protocol.TEvent;
import com.monkeydp.demo.thrift.server.EventService;
import com.monkeydp.demo.thrift.server.ThriftServerApplication;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.monkeydp.demo.thrift.protocol.TEventType.NOTIFY;

/**
 * Start thrift client before run test
 *
 * @author iPotato
 * @date 2019/7/11
 */
@SpringBootTest(classes = ThriftServerApplication.class)
public class EventServiceTest extends BaseTest {

    @Autowired
    EventService eventService;

    @Test
    public void getPublishingTest() {
        TEvent event = new TEvent(NOTIFY, "来自服务端的通知");
        String str = eventService.getPublishing(event);
        String expectedStr = String.format("收到事件: %s", event);
        Assert.assertEquals(expectedStr, str);
    }
}
