package com.example.calendarize.filter;

import com.example.calendarize.config.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtValidatorFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    public JwtValidatorFilter(JwtTokenProvider _jwtTokenProvider)
    {
        this.jwtTokenProvider = _jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response,@Nullable FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Jwt validate process");

        assert request != null;

        String jwt = jwtTokenProvider.resolveToken(request);

        Authentication authentication = jwtTokenProvider.validateToken(jwt);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        assert filterChain != null;
        filterChain.doFilter(request,response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/api/auth");
    }
}
