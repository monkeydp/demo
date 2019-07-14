package com.monkeydp.demo.protobuf.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author iPotato
 * @date 2019/7/14
 */
@Configuration
public class ProtobufConfig {

    /**
     * Protobuf 序列化
     *
     * @return
     */
    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    /**
     * Protobuf 反序列化
     *
     * @param protobufHttpMessageConverter
     * @return
     */
    @Bean
    RestTemplate restTemplate(ProtobufHttpMessageConverter protobufHttpMessageConverter) {
        return new RestTemplate(Collections.singletonList(protobufHttpMessageConverter));
    }
}
