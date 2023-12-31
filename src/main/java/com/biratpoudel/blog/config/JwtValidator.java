package com.biratpoudel.blog.config;

import com.biratpoudel.blog.exception.BadCredentialsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(AppConstants.JWT_HEADER);

        if (jwt != null) {
            try{
                String username = JwtProvider.getUsernameFromJwtToken(jwt);
                Set<GrantedAuthority> authorities = new HashSet<>();
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch (Exception ex) {
                throw new BadCredentialsException("Invalid Token!");
            }
        }

        filterChain.doFilter(request, response);

    }
}
