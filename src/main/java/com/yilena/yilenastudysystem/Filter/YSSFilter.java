package com.yilena.yilenastudysystem.Filter;

import com.yilena.yilenastudysystem.utils.CurrentHolder;
import com.yilena.yilenastudysystem.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class YSSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("进入过滤器");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI();

        if(url.contains("/login")){
            log.info("登录请求,放行");
            filterChain.doFilter(request,response);
            return;
        }

        String token = request.getHeader("token");

        if(token == null || token.isEmpty()){
            log.info("token为空");
            response.setStatus(401);
            return;
        }

        try {
            Claims claims =JwtUtils.parseToken(token);
            Integer id = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrent(id);
            log.info("token解析成功");
        } catch (Exception e) {
            log.info("token解析失败");
            response.setStatus(401);
            return;
        }
        filterChain.doFilter(request,response);
        CurrentHolder.removeCurrent();
    }
}
