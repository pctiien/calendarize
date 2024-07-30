package com.example.calendarize.service;

import com.example.calendarize.dto.UserDto;

import java.util.List;

public interface IAppUserService {
    UserDto updateAccount(UserDto dto);
    UserDto deleteAccount(UserDto dto);
    UserDto deleteAccount(String email);
    UserDto getAccountById(Long id);
    UserDto getAccountByEmail(String email);
    List<UserDto> getAllAccount();
}
