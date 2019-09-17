package com.dylan.licenses.clients;

import com.dylan.licenses.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {
    @Autowired
    RestTemplate restTemplate;
//    @Autowired
//    RestOperations restTemplate;
    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                "http://organizationservice/v1/organizations/{organizationId}",
                HttpMethod.GET,
                null, Organization.class, organizationId
        );
        return restExchange.getBody();
    }
}
