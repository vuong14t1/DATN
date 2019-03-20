package com.example.genealogy.config;

import com.example.genealogy.model.UserModel;
import com.example.genealogy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
// handle 403 page
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {

        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            System.out.println("User '" + auth.getName()
                    + "' attempted to access the protected URL: "
                    + httpServletRequest.getRequestURI());
        }
        System.out.println("into denined");

        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/");

    }
}
