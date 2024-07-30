package com.example.calendarize.mapper;

import com.example.calendarize.dto.SignupDto;
import com.example.calendarize.dto.UserDto;
import com.example.calendarize.entity.AppUser;

import java.util.List;
import java.util.stream.Collectors;

public class AppUserMapper {
    public static AppUser mapToAccount(SignupDto dto)
    {
        return AppUser.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();
    }
    public static AppUser mapToAccount(UserDto dto)
    {
        return AppUser.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
    }
    public static UserDto mapToDto(AppUser user)
    {
        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static List<UserDto> mapToDto(List<AppUser> accounts){
        return accounts.stream().map(account->UserDto.builder()
                .email(account.getEmail())
                .name(account.getName())
                .build()
        ).collect(Collectors.toList());
    }
}
