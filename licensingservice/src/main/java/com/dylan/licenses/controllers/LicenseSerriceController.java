package com.dylan.licenses.controllers;

import com.dylan.licenses.model.License;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1/organizations/{organizationId}/license")
public class LicenseSerriceController {
    @RequestMapping(value="/{licenseId}",method = RequestMethod.GET)
    public License getLicenses(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId){
        return new License()
                .withId(licenseId)
                .withProductName("Teleco")
                .withLicenseType("Seat")
                .withOrganizationId("TestOrg");
    }
}
