package com.monkeydp.demo.thrift.client.event;

import com.monkeydp.demo.thrift.client.BaseTest;
import com.monkeydp.demo.thrift.protocol.event.TEvent;
import com.monkeydp.demo.thrift.protocol.event.TEventService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.monkeydp.demo.thrift.protocol.event.TEventType.NOTIFY;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author iPotato
 * @date 2019/7/11
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class EventControllerTest extends BaseTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TProtocolFactory protocolFactory;

    private TEventService.Iface client;

    @Before
    public void before() throws TTransportException {
        String url = String.format("http://localhost:%s/event", port);
        TTransport transport = new THttpClient(url);
        TProtocol protocol = protocolFactory.getProtocol(transport);
        client = new TEventService.Client(protocol);
    }

    @Test
    public void greetTest() throws TException {
        TEvent event = new TEvent(NOTIFY, "来自服务端的通知");
        String str = client.publish(event);
        String expectedStr = EventUtil.receive(event);
        Assert.assertEquals(expectedStr, str);
    }
}