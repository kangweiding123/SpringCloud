package com.dylan.licenses.controllers;

import com.dylan.core.Exception.MyException;
import com.dylan.core.response.ResponsResultImpl;
import com.dylan.core.response.ResponseResult;
import com.dylan.licenses.config.ServiceConfig;
import com.dylan.licenses.model.License;
import com.dylan.licenses.services.LicenseService;
import com.dylan.licenses.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/v1/organizations/{organizationId}/license")
public class LicenseSerriceController {
    private static final Logger logger = LoggerFactory.getLogger(LicenseSerriceController.class);

    @Autowired
    private LicenseService licenseService;

    @Autowired
    ServiceConfig serviceConfig;

    @RequestMapping(value="/",method = RequestMethod.GET)
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        logger.info("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return licenseService.getLicensesByOrg(organizationId);
    }

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

    @RequestMapping(value = "/{licenseId}/{clientType}", method = RequestMethod.GET)
    public License getlicenseWithClient(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId,
            @PathVariable("clientType") String clientType){
        return licenseService.getLicense(organizationId,licenseId,clientType);
    }

    /**
     * 测试config配置文件是否可以读取
     * url: http://localhost:8881/v1/organizations/123/license/hello
     * @return
     */
    @RequestMapping("/hello")
    public ResponsResultImpl showUserAndPassword() throws Exception {
        String sb = "username is " + serviceConfig.getProperty();
        ResponseResult<String> responseResult = new ResponseResult();
        List<String> data = new ArrayList<>();
        data.add(sb);
        responseResult.setResult(data);
        responseResult.setTotal(0);
//        return ResponseResult.returnTimeOut();
//        return responseResult;
        throw new MyException("9999","自定义错误实验");
    }
}
