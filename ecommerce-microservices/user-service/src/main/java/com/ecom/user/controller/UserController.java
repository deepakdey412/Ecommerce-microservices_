package com.ecom.user.controller;

import com.ecom.user.dto.LoginRequestDto;
import com.ecom.user.dto.LoginResponseDto;
import com.ecom.user.dto.UserCreatedRequestDto;
import com.ecom.user.dto.UserResponseDto;
import com.ecom.user.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserCreatedRequestDto userCreatedRequestDto) {
        UserResponseDto response = userService.registerUser(userCreatedRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = userService.loginUser(loginRequestDto);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("id") Long id , Authentication authentication) {
        UserResponseDto response = userService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }





}
