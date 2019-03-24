package com.vuongpq2.datn.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication authentication)
            throws IOException, ServletException {
        final String[] result = {"/account/login"};
        authentication.getAuthorities().forEach(authority->{
            System.out.println("role" + authority.getAuthority());
            if(authority.getAuthority().equals("ROLE_ADMIN")){
                result[0] = "/admin";

            }else if(authority.getAuthority().equals("ROLE_USER") || authority.getAuthority().equals("ROLE_USER_FB")) {
                if(!result[0].equals("/admin")) {
                    result[0] = "/genealogy";
                }
            } else {
                throw new IllegalStateException();
            }
        });
        redirectStrategy.sendRedirect(arg0, arg1, result[0]);
    }

}