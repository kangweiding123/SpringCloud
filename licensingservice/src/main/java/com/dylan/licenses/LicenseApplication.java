package com.dylan.licenses;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients //使用Feign客户端
public class LicenseApplication {
    public static void main(String[] args) {
        SpringApplication.run(LicenseApplication.class,args);
    }

//    @Bean
//    @LoadBalanced //告诉Spring Cloud创建一个支持Ribbon的RestTemplate
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }
    @Bean
    @LoadBalanced
    public RestOperations restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
