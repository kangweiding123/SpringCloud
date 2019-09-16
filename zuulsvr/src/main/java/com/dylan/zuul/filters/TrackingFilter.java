package com.dylan.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author DylanKang
 * @Description: 所有Zuul过滤器必须扩展ZuulFilter类，并覆盖4个方法、
 * 即{@link TrackingFilter#filterType()}、{@link TrackingFilter#filterOrder()}、{@link TrackingFilter#shouldFilter()}、
 * {@link TrackingFilter#run()}、{@link TrackingFilter#filterType()}
 * @Date 2019/09/16
 */
@Component
public class TrackingFilter extends ZuulFilter {
    
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);


    // 在所有过滤器中使用的常用方法都封装在FilterUtils类中
    @Autowired
    FilterUtils filterUtils;


    @Override
    // filterType方法用于告诉Zuul，该过滤器是前置过滤器、路由过滤器还是后置过滤器
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    // filterOrder方法返回一个整数值，只是不同类型的过滤器的执行顺序
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    // shouldFilter方法返回一个布尔值来指示该过滤器是否需要执行
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    // run方法是每次服务通过过滤器时执行的代码。run方法检查tmx-correlation-id是否存在，如果不存在，则生成一个关联值，并设置HTTP首部tmx-correlation-id
    public Object run() throws ZuulException {
        if (isCorrelationIdPresent()) {
            logger.debug("tmx-correlation-id found in tracking filter: {}.", filterUtils.getCorrelationId());
        } else {
            filterUtils.setCorrelationId(generateCorrelationId());
            logger.debug("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        logger.debug("Processing incoming request for {}.", ctx.getRequest().getRequestURI());
        return null;
    }

    // 返回一个布尔值来指示该过滤器是否需要执行
    private boolean isCorrelationIdPresent() {
        if (filterUtils.getCorrelationId() != null) {
            return true;
        }
        return false;
    }

    // 该辅助方法实际上检查tmx-correlation-id是否存在，并且可以生成关联ID的GUID值
    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
