package com.example.calendarize.filter;

import com.example.calendarize.config.JwtTokenProvider;
import com.example.calendarize.constant.SecurityConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


public class JwtGeneratorFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtGeneratorFilter(JwtTokenProvider _jwtTokenProvider)
    {
        this.jwtTokenProvider = _jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            @Nullable HttpServletRequest request,@Nullable HttpServletResponse response,@Nullable FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("Jwt generate process");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String jwtToken = jwtTokenProvider.generateToken(authentication);

        System.out.printf("Jwt token generated : %s \n",jwtToken);

        assert response != null;
        response.setHeader(SecurityConstant.JWT_HEADER, jwtToken);

        assert filterChain != null;
        filterChain.doFilter(request, response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().startsWith("/api/auth/signin");
    }


}
