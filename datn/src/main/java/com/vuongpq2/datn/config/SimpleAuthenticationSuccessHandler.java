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
            if(authority.getAuthority().equals("ROLE_USER") || authority.getAuthority().equals("ROLE_USER_FB")){
                result[0] = "/genealogy";
//                try {
////                    redirectStrategy.sendRedirect(arg0, arg1, "");
//                    System.out.println("vao day user");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }else if(authority.getAuthority().equals("ROLE_ADMIN")) {
//                try {
//                    redirectStrategy.sendRedirect(arg0, arg1, "/admin");
//                    System.out.println("vao day admin");
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
                result[0] = "/admin";
            } else {
                throw new IllegalStateException();
            }
        });
        System.out.println("vao day login " + result[0]);
        redirectStrategy.sendRedirect(arg0, arg1, result[0]);
    }

}