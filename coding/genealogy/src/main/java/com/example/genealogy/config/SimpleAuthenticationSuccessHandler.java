package com.example.genealogy.config;


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
        authentication.getAuthorities().forEach(authority->{
            System.out.println("authen " + authority.getAuthority());
            if(authority.getAuthority().equals("ROLE_MEMBER") || authority.getAuthority().equals("ROLE_USER_FB")){
                try {
                    redirectStrategy.sendRedirect(arg0, arg1, "/genealogy/user");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else if(authority.getAuthority().equals("ROLE_ADMIN")) {
                try {
                    redirectStrategy.sendRedirect(arg0, arg1, "/admin");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                throw new IllegalStateException();
            }
        });
        redirectStrategy.sendRedirect(arg0, arg1, "/account/login");
    }

}