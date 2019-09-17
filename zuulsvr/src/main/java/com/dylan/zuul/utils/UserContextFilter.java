package com.dylan.zuul.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 这个过滤器使用Spring的@Component注解和实现一个javax.servlet.Filter接口来呗Spring注册与获取的
 */
@Component
public class UserContextFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 检索调用的HTTP首部中设置的值，将这些值付给存储在UserContextHolder中的UserContext
        UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
        // 如果使用在代码的README文件中定义的验证服务示例，那么从HTTP首部获得的其他值将发挥作用
        UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        UserContextHolder.getContext().setOrgId(httpServletRequest.getHeader(UserContext.ORG_ID));
        logger.info("UserContextFilter Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
