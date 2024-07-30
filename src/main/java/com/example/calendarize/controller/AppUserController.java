package com.example.calendarize.controller;

import com.example.calendarize.dto.UserDto;
import com.example.calendarize.entity.AppUser;
import com.example.calendarize.repository.AppUserRepository;
import com.example.calendarize.service.IAppUserService;
import com.example.calendarize.service.impl.AppUserServiceImpl;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppUserController {

    @Autowired
    private IAppUserService appUserService;

    @GetMapping("/user")
    public ResponseEntity<?> getAllUsers()
    {
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.getAllAccount());
    }
    @GetMapping("user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.getAccountById(id));
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateAppUser(@RequestBody UserDto dto)
    {
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.updateAccount(dto));
    }



}
