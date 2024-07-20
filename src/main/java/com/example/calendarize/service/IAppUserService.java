package com.example.calendarize.service;

import com.example.calendarize.dto.AppUserDto;

import java.util.List;

public interface IAppUserService {
    AppUserDto updateAccount(AppUserDto dto);
    AppUserDto deleteAccount(AppUserDto dto);
    AppUserDto deleteAccount(String email);
    AppUserDto getAccountById(Long id);
    AppUserDto getAccountByEmail(String email);
    List<AppUserDto> getAllAccount();
}
