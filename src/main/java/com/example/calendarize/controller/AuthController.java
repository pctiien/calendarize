package com.example.calendarize.controller;

import com.example.calendarize.dto.AppUserDto;
import com.example.calendarize.dto.LoginDto;
import com.example.calendarize.repository.AppUserRepository;
import com.example.calendarize.service.IAuthService;
import com.example.calendarize.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<LoginDto> signIn(@RequestBody LoginDto dto)
    {
        return ResponseEntity.ok(authService.signIn(dto));
    }
    @PostMapping("/signup")
    public ResponseEntity<AppUserDto> signUp(@RequestBody AppUserDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(dto));
    }



}
