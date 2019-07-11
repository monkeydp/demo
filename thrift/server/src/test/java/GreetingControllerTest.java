import com.monkeydp.demo.thrift.protocol.TGreetingService;
import com.monkeydp.demo.thrift.protocol.TName;
import com.monkeydp.demo.thrift.server.GreetingUtil;
import com.monkeydp.demo.thrift.server.ThriftServerApplication;
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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author iPotato
 * @date 2019/7/11
 */
@SpringBootTest(
        classes = {ThriftServerApplication.class},
        webEnvironment = RANDOM_PORT
)
public class GreetingControllerTest extends BaseTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TProtocolFactory protocolFactory;

    private TGreetingService.Iface client;

    @Before
    public void before() throws TTransportException {
        String url = String.format("http://localhost:%s/api", port);
        TTransport transport = new THttpClient(url);
        TProtocol protocol = protocolFactory.getProtocol(transport);
        client = new TGreetingService.Client(protocol);
    }

    @Test
    public void greetTest() throws TException {
        TName name = new TName("John", "Smith");
        String str = client.greet(name);
        String expectedStr = GreetingUtil.hello(name);
        Assert.assertEquals(expectedStr, str);
    }
}