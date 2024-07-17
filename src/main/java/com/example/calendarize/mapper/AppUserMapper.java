package com.example.calendarize.mapper;

import com.example.calendarize.dto.AppUserDto;
import com.example.calendarize.entity.AppUser;

import java.util.List;
import java.util.stream.Collectors;

public class AppUserMapper {
    public static AppUser mapToAccount(AppUserDto dto)
    {
        return AppUser.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();
    }
    public static AppUserDto mapToDto(AppUser user)
    {
        return AppUserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }
    public static List<AppUser> mapToAccount(List<AppUserDto> dtos){
        return dtos.stream().map(dto->AppUser.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .build()
        ).collect(Collectors.toList());
    }
    public static List<AppUserDto> mapToDto(List<AppUser> accounts){
        return accounts.stream().map(account->AppUserDto.builder()
                .email(account.getEmail())
                .password(account.getPassword())
                .name(account.getName())
                .build()
        ).collect(Collectors.toList());
    }
}
