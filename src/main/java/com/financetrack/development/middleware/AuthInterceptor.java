package com.financetrack.development.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.financetrack.development.security.JwtUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception{
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        try {
            String token = null;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }



            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: No token found");
                return false;
            }

            String userId = jwtUtils.verifyTokenAndGetUserById(token);

            request.setAttribute("userId", userId);

            return true; 
        } catch (Exception e) {
            System.out.println("not working inside authinterceptor");
            // e.printStackTrace();
            return false;
        }
    }
    
}
