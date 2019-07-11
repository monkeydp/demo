package com.monkeydp.demo.thrift.client;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

/**
 * Start thrift server before run test
 *
 * @author iPotato
 * @date 2019/7/11
 */
@SpringBootTest
public class GreetingServiceTest extends BaseTest {

    @Autowired
    GreetingService greetingService;

    @Test
    public void getGreetingTest() {
        String firstName = "John";
        String secondName = "Smith";
        String str = greetingService.getGreeting(firstName, secondName);
        String expectedStr = String.format("Hello %s %s", firstName, secondName);
        assertEquals(expectedStr, str);
    }
}
