package com.example.calendarize.service;

import com.example.calendarize.dto.AuthenticationResponse;
import com.example.calendarize.dto.LoginDto;
import com.example.calendarize.dto.SignupDto;
import com.example.calendarize.dto.UserDto;
import com.example.calendarize.entity.AppUser;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    AuthenticationResponse signIn(LoginDto dto, HttpServletResponse response);
    SignupDto signUp(SignupDto dto);
    void saveToken(AppUser user, String token);
}
