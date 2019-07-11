package com.monkeydp.demo.thrift.client;

import com.monkeydp.demo.thrift.protocol.TGreetingService;
import com.monkeydp.demo.thrift.protocol.TName;
import info.developerblog.spring.thrift.annotation.ThriftClient;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 * @author iPotato
 * @date 2019/7/11
 */
@Service
public class GreetingService {

    @ThriftClient(serviceId = "greeting-service", path = "/api")
    TGreetingService.Client client;

    @SneakyThrows
    public String getGreeting(String firstName, String lastName) {
        return client.greet(new TName(firstName, lastName));
    }
}
