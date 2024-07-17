package com.example.calendarize.controller;

import com.example.calendarize.dto.AppUserDto;
import com.example.calendarize.entity.AppUser;
import com.example.calendarize.repository.AppUserRepository;
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
    private AppUserServiceImpl appUserService;

    @GetMapping("/user")
    public ResponseEntity<List<AppUserDto>> getAllUsers()
    {
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.getAllAccount());
    }
    @GetMapping("user/{id}")
    public ResponseEntity<AppUserDto> getUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.getAccountById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserDto> createAppUser(@RequestBody AppUserDto dto)
    {
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.createAccount(dto));
    }

    @PutMapping("/user")
    public ResponseEntity<AppUserDto> updateAppUser(@RequestBody AppUserDto dto)
    {
        return ResponseEntity.status(HttpStatus.OK).body(appUserService.updateAccount(dto));
    }



}
