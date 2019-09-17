package com.dylan.licenses;
import com.dylan.licenses.utils.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;


@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients //使用Feign客户端
@EnableCircuitBreaker //使用Hystrix
@ComponentScan(basePackages = "com.dylan")
public class LicenseApplication {
    public static void main(String[] args) {
        SpringApplication.run(LicenseApplication.class,args);
    }

    @Bean
    @LoadBalanced //告诉Spring Cloud创建一个支持Ribbon的RestTemplate
    public RestTemplate getRestTemplate() {
        // 之前的获取RestTemplate方法
//        return new RestTemplate();
        RestTemplate template = new RestTemplate();
        List interceptors = template.getInterceptors();
        if (interceptors==null){
            template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        }
        else{
            interceptors.add(new UserContextInterceptor());
            template.setInterceptors(interceptors);
        }

        return template;
    }
//    @Bean
//    @LoadBalanced
//    public RestOperations restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }
}
