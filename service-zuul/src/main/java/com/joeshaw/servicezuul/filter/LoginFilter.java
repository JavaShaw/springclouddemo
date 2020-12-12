package com.joeshaw.servicezuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.constants.ZuulConstants;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(LoginFilter.class);
    @Override
    public String filterType() {  //pre：路由之前 routing：路由之时 post： 路由之后 error：发送错误调用
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {  //有很多过滤器,过滤的顺序,值越小,越先执行,优先级越高
        return 0;
    }

    @Override
    public boolean shouldFilter() { //过滤器是否生效
        //例如拦截某些接口
        return true;
    }

    //shuoldFilter返回true的时候
    @Override
    public Object run() {   //过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }
        log.info("ok");
        return null;
    }
}
