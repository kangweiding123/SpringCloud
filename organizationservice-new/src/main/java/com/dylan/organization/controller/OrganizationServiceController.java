package com.dylan.organization.controller;

import com.dylan.organization.model.Organization;
import com.dylan.organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1/organizations")
public class OrganizationServiceController {

    @Autowired
    private OrganizationService orgService;


    @RequestMapping(value="/{organizationId}",method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        Organization organization = orgService.getOrg(organizationId);
        organization.setContactName("NEW::" + organization.getContactName());
        organization.setContactPhone("NEW::" + organization.getContactPhone());
        return organization;
    }
}
