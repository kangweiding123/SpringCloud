package com.dylan.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author DylanKang
 * @Description:
 * @Date 2019/9/19
 */
@Component
public class ResponseFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    @Autowired
    FilterUtils filterUtils;

    @Override
    // 要构建一个后置过滤器，需要设置过滤器的类型为POST_FILTER_TYPE
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        logger.info("Adding the correlation id to the outbound headers. {}",filterUtils.getCorrelationId());
        // 获取原始HTTP请求中传入的关联ID，并将它设置到响应中
        ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID,filterUtils.getCorrelationId());
        // 记录传出的请求URI，这样就有了“书档”，他将显示进入Zuul的用户请求的传入和传出条目
        logger.info("Completing outgoing request for {}.",ctx.getRequest().getRequestURI());
        logger.info("Response body {}",ctx.getResponseBody());
        return null;
    }
}
