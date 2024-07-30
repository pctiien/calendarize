package com.example.calendarize.service.impl;

import com.example.calendarize.dto.UserDto;
import com.example.calendarize.entity.AppUser;
import com.example.calendarize.exception.ObjAlreadyExistsException;
import com.example.calendarize.exception.ResourceNotFoundException;
import com.example.calendarize.mapper.AppUserMapper;
import com.example.calendarize.repository.AppUserRepository;
import com.example.calendarize.service.IAppUserService;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements IAppUserService {

    @Autowired
    private AppUserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public UserDto updateAccount(UserDto dto) {
        if(dto == null)
        {
            throw new NullPointerException("DTO is null");
        }
        Optional<AppUser> optionalAppUser = repository.findAppUserByEmail(dto.getEmail());
        if(optionalAppUser.isEmpty())
        {
            throw new ResourceNotFoundException("User","Email",dto.getEmail());
        }
        AppUser user = AppUserMapper.mapToAccount(dto);
        user.setId(optionalAppUser.get().getId());
        repository.save(user);
        return dto;
    }

    @Transactional
    @Override
    public UserDto deleteAccount(UserDto dto) {
        if(dto == null)
        {
            throw new NullPointerException("DTO is null");
        }
        Optional<AppUser> optionalAppUser = repository.findAppUserByEmail(dto.getEmail());
        if(optionalAppUser.isEmpty())
        {
            throw new ResourceNotFoundException("User","Email",dto.getEmail());
        }
        repository.delete(optionalAppUser.get());
        return dto;
    }

    @Transactional
    @Override
    public UserDto deleteAccount(String email) {
        return null;
    }

    @Override
    public UserDto getAccountById(Long id) {
        Optional<AppUser> optionalAppUser = repository.findById(id);
        if(optionalAppUser.isEmpty())
        {
            throw new ResourceNotFoundException("User","Id",String.valueOf(id));
        }
        return AppUserMapper.mapToDto(optionalAppUser.get());
    }

    @Override
    public UserDto getAccountByEmail(String email) {
        Optional<AppUser> optionalAppUser = repository.findAppUserByEmail(email);
        if(optionalAppUser.isEmpty())
        {
            throw new ResourceNotFoundException("User","Id",email);
        }
        return AppUserMapper.mapToDto(optionalAppUser.get());
    }

    @Override
    public List<UserDto> getAllAccount() {
        return AppUserMapper.mapToDto(repository.findAll());
    }
}
