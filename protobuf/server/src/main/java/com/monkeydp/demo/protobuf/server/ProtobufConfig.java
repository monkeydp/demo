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
     * Protobuf Http Message 转换器
     *
     * @return
     */
    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    /**
     * RestTemplate 中添加转换器
     *
     * @param protobufHttpMessageConverter
     * @return
     */
    @Bean
    RestTemplate restTemplate(ProtobufHttpMessageConverter protobufHttpMessageConverter
    ) {
        return new RestTemplate(Collections.singletonList(
                protobufHttpMessageConverter
        ));
    }
}
