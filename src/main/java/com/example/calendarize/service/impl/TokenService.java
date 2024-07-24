package com.example.calendarize.service.impl;

import com.example.calendarize.constant.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenService {
    @Value("${spring.security.jwt.secret-key}")
    private String secretKey ;
    @Value("${spring.security.jwt.expiration}")
    private Long expirationTime;
    @Value("${spring.security.jwt.refresh-expiration}")
    private Long refreshExpiration;
    private SecretKey key;

    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public String generateAccessToken(Authentication authentication)
    {
        return generateToken(authentication,expirationTime);
    }
    public String generateRefreshToken(Authentication authentication)
    {
        return generateToken(authentication,refreshExpiration);
    }


    public String generateToken(Authentication authentication,Long expirationTime) {
        if (null != authentication) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiredDate = now.plus(Duration.ofMillis(expirationTime));
            return Jwts.builder()
                    .issuer("PhanCongTien")
                    .subject("JWT Token")
                    .claim(SecurityConstant.JWT_Email, authentication.getName())
                    .claim(SecurityConstant.JWT_Authority, authentication.getAuthorities().stream().map(
                            GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .issuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                    .expiration(Date.from(expiredDate.atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(key)
                    .compact();
        }else{
            throw new BadCredentialsException("Invalid credentials");
        }
    }
    public String resolveToken(HttpServletRequest request)
    {
        return request.getHeader(SecurityConstant.JWT_HEADER);
    }
    public Authentication validateToken(String token){
        if(null!=token)
        {
            try {
                Claims claims = Jwts.parser().verifyWith(key)
                        .build().parseSignedClaims(token).getPayload();
                String email = String.valueOf(claims.get(SecurityConstant.JWT_Email));
                String authorities = String.valueOf(claims.get(SecurityConstant.JWT_Authority));
                if(isTokenExpired(claims))
                {
                    throw new BadCredentialsException("Expired token");

                }
                return new UsernamePasswordAuthenticationToken(email, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

            }
            catch (Exception e)
            {
                throw new BadCredentialsException("Invalid token received");

            }
        }else{
            throw new NullPointerException("Null jwt token");
        }
    }
    private boolean isTokenExpired(Claims claims) {
        LocalDateTime now = LocalDateTime.now();
        Date expiredDate = extractExpiration(claims);
        Date currentDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        return expiredDate.before(currentDate);
    }

    public String extractEmail(Claims claims)
    {
        return String.valueOf(claims.get(SecurityConstant.JWT_Email));
    }
    private Date extractExpiration(Claims claims)
    {
        return claims.getExpiration();
    }
    private String extractAuthority(Claims claims)
    {
        return String.valueOf(claims.get(SecurityConstant.JWT_Authority));
    }


}
