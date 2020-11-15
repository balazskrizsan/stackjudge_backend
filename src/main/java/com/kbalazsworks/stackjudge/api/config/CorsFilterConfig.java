package com.kbalazsworks.stackjudge.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilterConfig implements Filter
{
    private static final Logger logger = LoggerFactory.getLogger(CorsFilterConfig.class);

    @Value("${HTTP_CORS_ORIGINS}")
    String aclOrigin;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException
    {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest  request  = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", getAclOrigin(request));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader(
            "Access-Control-Allow-Headers",
            "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization"
        );

        if ("OPTIONS".equalsIgnoreCase(request.getMethod()))
        {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else
        {
            chain.doFilter(req, res);
        }
    }

    private String getAclOrigin(HttpServletRequest request)
    {
//        if (request.getRequestURI().equals(HealthCheckConstants.ACTION_HEALTH_CHECK))
//        {
//            return "*";
//        }

        return aclOrigin;
    }

    public void init(FilterConfig filterConfig)
    {
    }

    public void destroy()
    {
    }
}
