package com.monkeydp.demo.thrift.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author iPotato
 * @date 2019/7/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {ThriftClientApplication.class},
        webEnvironment = RANDOM_PORT
)
public class GreetingServiceTest {

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
