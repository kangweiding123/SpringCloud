package com.dylan.licenses.controllers;

import com.dylan.licenses.config.ServiceConfig;
import com.dylan.licenses.model.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1/organizations/{organizationId}/license")
public class LicenseSerriceController {

    @Autowired
    ServiceConfig serviceConfig;

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

    /**
     * 测试config配置文件是否可以读取
     * url: http://localhost:8881/v1/organizations/123/license/hello
     * @return
     */
    @RequestMapping("/hello")
    public String showUserAndPassword(){
        String sb = "username is " + serviceConfig.getProperty();
        return sb;
    }
}
