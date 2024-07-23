package com.example.calendarize.controller;

import com.example.calendarize.dto.AppUserDto;
import com.example.calendarize.dto.LoginDto;
import com.example.calendarize.repository.AppUserRepository;
import com.example.calendarize.service.IAppUserService;
import com.example.calendarize.service.IAuthService;
import com.example.calendarize.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AppUserDto> signUp(@RequestBody AppUserDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(dto));
    }
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginDto dto, HttpServletResponse response)
    {
        return ResponseEntity.ok(authService.signIn(dto,response));
    }




}
