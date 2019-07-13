package com.monkeydp.demo.protobuf.protocol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author iPotato
 * @date 2019/7/11
 */
@EnableAsync
@SpringBootApplication
public class ProtobufProtocolApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProtobufProtocolApplication.class, args);
    }
}