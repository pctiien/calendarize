package com.example.calendarize.config;

import com.example.calendarize.entity.AppUser;
import com.example.calendarize.entity.Authority;
import com.example.calendarize.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class EmailPwdAuthenticationProvider implements AuthenticationProvider {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmailPwdAuthenticationProvider(AppUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        System.out.println("hehe");

        assert repository != null;
        Optional<AppUser> user = repository.findAppUserByEmail(email);

        if(user.isEmpty())
        {
            throw new BadCredentialsException("User is not existed");
        }
        if(passwordEncoder.matches(pwd,user.get().getPassword()))
        {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email,pwd,getGrantedAuthorities(user.get().getAuthorities()));
            SecurityContextHolder.getContext().setAuthentication(token);
            return token;
        }else{

            throw new BadCredentialsException("Invalid password");
        }
    }
    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities)
    {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorities.forEach(authority->{
            authorityList.add(new SimpleGrantedAuthority(authority.getName()));
        });
        return authorityList;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
