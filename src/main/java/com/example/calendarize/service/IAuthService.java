package com.example.calendarize.service;

import com.example.calendarize.dto.AppUserDto;
import com.example.calendarize.dto.AuthenticationResponse;
import com.example.calendarize.dto.LoginDto;
import com.example.calendarize.entity.AppUser;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    AuthenticationResponse signIn(LoginDto dto, HttpServletResponse response);
    AppUserDto signUp(AppUserDto dto);
    void saveToken(AppUser user, String token);
}
