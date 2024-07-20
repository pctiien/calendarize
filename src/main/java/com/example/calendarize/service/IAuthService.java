package com.example.calendarize.service;

import com.example.calendarize.dto.AppUserDto;
import com.example.calendarize.dto.LoginDto;

public interface IAuthService {
    LoginDto signIn(LoginDto dto);
    AppUserDto signUp(AppUserDto dto);
}
