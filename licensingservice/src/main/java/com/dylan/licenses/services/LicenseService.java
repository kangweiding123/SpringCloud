package com.dylan.licenses.services;

import com.dylan.licenses.clients.OrganizationDiscoveryClient;
import com.dylan.licenses.clients.OrganizationFeignClient;
import com.dylan.licenses.clients.OrganizationRestTemplateClient;
import com.dylan.licenses.config.ServiceConfig;
import com.dylan.licenses.model.License;
import com.dylan.licenses.model.Organization;
import com.dylan.licenses.repository.LicenseRepository;
import com.dylan.licenses.utils.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//设置默认值，整个类所有的熔断器Hystrix的超时时间都为设置的值
@DefaultProperties(
        commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
        }
)
@Service
public class LicenseService {
    Logger logger = LoggerFactory.getLogger(LicenseService.class);

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    ServiceConfig config;

    @Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;


    @HystrixCommand
    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization org = retrieveOrgInfo(organizationId, clientType);

        return license
                .withOrganizationName(org.getName())
                .withContactName(org.getContactName())
                .withContactEmail(org.getContactEmail())
                .withContactPhone(org.getContactPhone())
                .withComment(config.getProperty());
    }

    //fallbackMethod属性定义了类中的一个方法，如果来自Hystrix的调用失败，那么就会调用该方法
    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList",
            //定义线程池惟一的名称
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties = {
                    //定义线程池中现成的最大数量
                    @HystrixProperty(name = "coreSize", value = "30"),
                    //定义线程池前的队列，可以对传入的请求进行排队，-1时不允许排队
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            },
            commandProperties = {
                    //用于设置断路器的超时时间，默认1秒
                    @HystrixProperty(
                            name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "1000"
                    ),
                    //控制Hystrix考虑将该断路器跳闸之前，在10s之内必须发生的连续调用数量，默认值为20
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    //超过调用数量的值之后在断路器跳闸前必须达到的调用失败（超时、抛出异常或HTTP500）的百分比
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    //在断路器跳闸之后，Hystrix允许另一个调用通过以便以便查看服务是否恢复健康之前Hystrix的休眠时间
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
                    //控制Hystrix用来监视服务调用问题的窗口大小，默认值为10000ms 即10s
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    //控制在定义的滚动窗口中收集统计信息的次数，此值必须被窗口大小整除
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")
            })
    public List<License> getLicensesByOrg(String organizationId) {
        logger.info("LicenseService.getLicensesByOrg  Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        randomlyRunLong(); //模拟超时
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackList = new ArrayList<>();
        License license = new License()
                .withId("0000000-00-00000")
                .withOrganizationId(organizationId)
                .withProductName(
                        "Sorry no licensing information currently available");
        fallbackList.add(license);
        return fallbackList;
    }

    public void saveLicense(License license) {
        license.withId(UUID.randomUUID().toString());

        licenseRepository.save(license);

    }

    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense(License license) {
        licenseRepository.delete(license);
    }

    /**
     * 模拟超时方法，3次会有1次触发
     */
    private void randomlyRunLong() {
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        if (randomNum == 3) {
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
