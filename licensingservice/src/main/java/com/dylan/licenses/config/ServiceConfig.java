package com.dylan.licenses.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class ServiceConfig {
    @Value("${example.property}")
    private String property;

    public String getProperty() {
        return  property;
    }
}
