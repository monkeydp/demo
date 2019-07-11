package com.monkeydp.demo.thrift.server.event;

import com.monkeydp.demo.thrift.protocol.event.TEvent;
import com.monkeydp.demo.thrift.server.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.monkeydp.demo.thrift.protocol.event.TEventType.NOTIFY;

/**
 * Start thrift client before run test
 *
 * @author iPotato
 * @date 2019/7/11
 */
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
