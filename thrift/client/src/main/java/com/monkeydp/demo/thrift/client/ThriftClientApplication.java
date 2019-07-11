package com.monkeydp.demo.thrift.client;

import com.monkeydp.demo.thrift.protocol.TGreetingService;
import info.developerblog.spring.thrift.annotation.ThriftClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author iPotato
 * @date 2019/7/11
 */
@EnableAsync
@SpringBootApplication
public class ThriftClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThriftClientApplication.class, args);
    }
}