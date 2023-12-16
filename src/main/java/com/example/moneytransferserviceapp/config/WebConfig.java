package com.example.moneytransferserviceapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${cross.origin}")
    private String origins;
    private static final String TRANSFER_MAPPING = "/transfer";
    private static final String CONFIRM_MAPPING = "/confirmOperation";
    private static final String HTTP_METHOD = "POST";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(TRANSFER_MAPPING)
                .allowedOrigins(origins)
                .allowedMethods(HTTP_METHOD);

        registry.addMapping(CONFIRM_MAPPING)
                .allowedOrigins(origins)
                .allowedMethods(HTTP_METHOD);
    }
}