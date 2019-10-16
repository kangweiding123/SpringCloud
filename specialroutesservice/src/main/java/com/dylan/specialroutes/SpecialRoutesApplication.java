package com.dylan.specialroutes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import com.dylan.specialroutes.utils.UserContextFilter;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.Filter;

/**
 * @author DylanKang
 * @Description:
 * @Date 2019/10/12
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = "com.dylan")
public class SpecialRoutesApplication {
    @Bean
    public Filter userContextFilter() {
        UserContextFilter userContextFilter = new UserContextFilter();
        return userContextFilter;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpecialRoutesApplication.class, args);
    }
}
