package com.dylan.licenses.clients;

import com.dylan.licenses.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("zuulservice/api/organization")
@Component
public interface OrganizationFeignClient {
    @RequestMapping(method = RequestMethod.GET,
    value = "/v1/organizations/{organizationId}",
    consumes = "application/json")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
