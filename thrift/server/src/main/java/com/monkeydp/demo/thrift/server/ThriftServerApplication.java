package com.monkeydp.demo.thrift.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author iPotato
 * @date 2019/7/11
 */
@EnableAsync
@SpringBootApplication
public class ThriftServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThriftServerApplication.class, args);
    }
}