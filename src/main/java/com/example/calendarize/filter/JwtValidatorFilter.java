package com.example.calendarize.filter;

import com.example.calendarize.config.JwtTokenProvider;
import com.example.calendarize.constant.SecurityConstant;
import com.example.calendarize.entity.Token;
import com.example.calendarize.repository.TokenRepository;
import com.example.calendarize.service.impl.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtValidatorFilter extends OncePerRequestFilter {


    private final TokenService tokenService;

    private final TokenRepository tokenRepository;


    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response,@Nullable FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Jwt validate process");

        assert request != null;

        String jwt = tokenService.resolveToken(request);

        if(!jwt.startsWith(SecurityConstant.JWT_BEARER)){
            assert filterChain != null;
            filterChain.doFilter(request,response);
            return;
        }

        jwt = jwt.substring(SecurityConstant.JWT_BEARER.length());

        Authentication authentication = tokenService.validateToken(jwt);
        Optional<Token> storedToken = tokenRepository.findByToken(jwt);
        if(storedToken.isPresent())
        {
            if(!storedToken.get().isExpired() && !storedToken.get().isRevoked())
            {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        assert filterChain != null;
        filterChain.doFilter(request,response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/api/auth");
    }
}
