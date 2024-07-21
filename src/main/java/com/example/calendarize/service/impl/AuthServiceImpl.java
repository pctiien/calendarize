package com.example.calendarize.service.impl;

import com.example.calendarize.dto.AppUserDto;
import com.example.calendarize.dto.LoginDto;
import com.example.calendarize.entity.AppUser;
import com.example.calendarize.exception.ObjAlreadyExistsException;
import com.example.calendarize.mapper.AppUserMapper;
import com.example.calendarize.repository.AppUserRepository;
import com.example.calendarize.service.IAuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AppUserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AppUserDto signUp(AppUserDto dto) {
        if(dto == null) throw new NullPointerException("DTO is null");
        Optional<AppUser> optionUser = repository.findAppUserByEmail(dto.getEmail());
        if(optionUser.isPresent()) throw new ObjAlreadyExistsException("This email already existed");
        AppUser user = AppUserMapper.mapToAccount(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(user);
        return dto;
    }
    @Override
    public LoginDto signIn(LoginDto dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
               // SecurityContextHolder.getContext().setAuthentication(authentication);
            return dto;
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
