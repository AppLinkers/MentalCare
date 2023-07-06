package com.example.mentalCare.common.advice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, redirectUrl);
        } else {
            String redirectUrl;
            if (authentication.getAuthorities().toArray()[0].toString().equals("PLAYER")) {
                redirectUrl = "/player/profile";
            } else if (authentication.getAuthorities().toArray()[0].toString().equals("DIRECTOR")) {
                redirectUrl = "/director/profile";
            } else if (authentication.getAuthorities().toArray()[0].toString().equals("ADMIN")) {
                redirectUrl = "/admin/user";
            } else {
                redirectUrl = getDefaultTargetUrl();
            }
            redirectStrategy.sendRedirect(request, response, redirectUrl);
        }
    }
}
