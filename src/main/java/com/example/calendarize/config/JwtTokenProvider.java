package com.example.calendarize.config;

import com.example.calendarize.constant.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;


@Component
@Data
public class JwtTokenProvider {
    @Value("${spring.security.jwt.secret-key}")
    private String secretKey ;
    @Value("${spring.security.jwt.expiration}")
    private Long expirationTime;
    private SecretKey key;

    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        if (null != authentication) {
            Date now = new Date();
            return Jwts.builder()
                    .issuer("PhanCongTien")
                    .subject("JWT Token")
                    .claim("email", authentication.getName())
                    .claim("authorities", authentication.getAuthorities().stream().map(
                            GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .issuedAt(new Date())
                    .expiration(new Date(now.getTime() + expirationTime))
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
                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));
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







}
