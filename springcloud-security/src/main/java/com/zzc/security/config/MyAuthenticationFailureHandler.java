package com.zzc.security.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author 张真诚
 * @Date 2019/10/16
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final String MAXIMUM_SESSION_MESSAGE="%E8%AF%A5%E8%B4%A6%E5%8F%B7%E5%B7%B2%E7%BB%8F%E8%A2%AB%E7%99%BB%E5%BD%95%E4%BA%86%E3%80%82";
    private final String WRONG_USERNAME_OR_PASSWORD="%E7%94%A8%E6%88%B7%E5%90%8D%E6%88%96%E5%AF%86%E7%A0%81%E4%B8%8D%E6%AD%A3%E7%A1%AE";
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            if(exception instanceof SessionAuthenticationException && exception.getMessage().contains("Maximum sessions")&&exception.getMessage().contains("for this principal exceeded")){
                //禁止多个用户登录
                response.setCharacterEncoding("utf-8");
                response.setStatus(200);
                response.sendRedirect("/login?error="+MAXIMUM_SESSION_MESSAGE);
            }
            if(exception instanceof  org.springframework.security.authentication.BadCredentialsException){
                //禁止多个用户登录
                response.setCharacterEncoding("utf-8");
                response.setStatus(200);
                response.sendRedirect("/login?error="+WRONG_USERNAME_OR_PASSWORD);
            }

    }
}
