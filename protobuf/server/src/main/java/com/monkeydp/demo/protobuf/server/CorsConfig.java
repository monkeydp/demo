package com.monkeydp.demo.protobuf.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS: Cross-origin resource sharing
 *
 * @author iPotato
 * @date 2019/7/15
 */
@Configuration
public class CorsConfig {
    /**
     * Allow CORS
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("*")
                        .allowCredentials(true)
                        .exposedHeaders("X-Protobuf-Message");
            }
        };
    }
}
