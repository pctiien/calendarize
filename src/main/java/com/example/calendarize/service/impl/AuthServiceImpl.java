package com.example.calendarize.service.impl;

import com.example.calendarize.constant.SecurityConstant;
import com.example.calendarize.dto.AuthenticationResponse;
import com.example.calendarize.dto.LoginDto;
import com.example.calendarize.dto.SignupDto;
import com.example.calendarize.dto.UserDto;
import com.example.calendarize.entity.AppUser;
import com.example.calendarize.entity.Token;
import com.example.calendarize.entity.TokenType;
import com.example.calendarize.exception.ObjAlreadyExistsException;
import com.example.calendarize.mapper.AppUserMapper;
import com.example.calendarize.repository.AppUserRepository;
import com.example.calendarize.repository.TokenRepository;
import com.example.calendarize.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
    private final AppUserRepository appUserRepository;
    /*@Autowired
    public AuthServiceImpl(AppUserRepository _repository, PasswordEncoder _passwordEncoder, AuthenticationManager _authenticationManager){
        this.repository = _repository;
        this.passwordEncoder = _passwordEncoder;
        this.authenticationManager = _authenticationManager;
    }*/


    @Override
    @Transactional
    public SignupDto signUp(SignupDto dto) {
        if(dto == null) throw new NullPointerException("DTO is null");
        Optional<AppUser> optionUser = repository.findAppUserByEmail(dto.getEmail());
        if(optionUser.isPresent()) throw new ObjAlreadyExistsException("This email already existed");
        AppUser user = AppUserMapper.mapToAccount(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(user);
        return dto;
    }

    @Transactional
    @Override
    public void saveToken(AppUser user, String token) {
        Token jwtToken = Token.builder()
                .token(token)
                .tokenType(TokenType.BEARER)
                .user(user)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(jwtToken);
    }

    @Transactional
    @Override
    public AuthenticationResponse signIn(LoginDto dto, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
            Optional<AppUser> user = appUserRepository.findAppUserByEmail(authentication.getName());
            assert user.isPresent();
            String accessToken = tokenService.generateAccessToken(authentication);
            saveToken(user.get(),accessToken);
            response.setHeader(SecurityConstant.JWT_HEADER,SecurityConstant.JWT_BEARER + accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return AuthenticationResponse.builder().accessToken(accessToken).build();
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
