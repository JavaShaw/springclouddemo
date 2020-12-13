package com.joeshaw.servicezuul.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 接口限流,谷歌guava模拟订单限流,正式部署会用nginx+网关限流
 */
@Component
public class RateLimiterFilter extends ZuulFilter {
    //每秒产生一百个令牌
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -4;
    }

    @Override
    public boolean shouldFilter() {
        //模拟订单接口做限流
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if("/apigateway/order/api/v1/order/save".equalsIgnoreCase(request.getRequestURI())){
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if(RATE_LIMITER.tryAcquire()) //限流非阻塞方式
            ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
        return null;
    }
}
