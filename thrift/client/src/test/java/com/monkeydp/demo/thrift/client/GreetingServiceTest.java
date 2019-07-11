package com.monkeydp.demo.thrift.client;

import com.monkeydp.demo.thrift.protocol.TName;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Start thrift server before run test
 *
 * @author iPotato
 * @date 2019/7/11
 */
public class GreetingServiceTest extends BaseTest {

    @Autowired
    GreetingService greetingService;

    @Test
    public void getGreetingTest() {
        TName name = new TName("John", "Smith");
        String str = greetingService.getGreeting(name);
        String expectedStr = String.format("Hello %s %s", name.getFirstName(), name.getSecondName());
        assertEquals(expectedStr, str);
    }
}
