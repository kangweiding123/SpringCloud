package com.dylan.licenses.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {
    @Value("${example.property}")
    private String property;

    public String getProperty() {
        return  property;
    }
}
