package com.dylan.licenses.clients;

import com.dylan.licenses.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrganizationDiscoveryClient {
    @Autowired
    private DiscoveryClient discoveryClient; //DiscoveryClient被自动注入这个类

    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        // 获取组织服务的所有实例的列表
        List<ServiceInstance> instanceList = discoveryClient.getInstances("organizationservice");

        if (instanceList.size()==0) {
            return null;
        }
        // 检索要调用的服务端点
        String serviceUri = String.format("%s/v1/organizations/%s",
                instanceList.get(0).getUri().toString(),
                organizationId);
        //使用标准的Spring REST模板类去调用服务
        ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUri,HttpMethod.GET,null,Organization.class,organizationId);

        return restExchange.getBody();
    }
}
