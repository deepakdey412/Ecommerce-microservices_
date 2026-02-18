package com.ecom.user.services;

import com.ecom.user.dto.LoginRequestDto;
import com.ecom.user.dto.LoginResponseDto;
import com.ecom.user.dto.UserCreatedRequestDto;
import com.ecom.user.dto.UserResponseDto;

public interface IUserService{
    UserResponseDto registerUser(UserCreatedRequestDto userCreatedRequestDto);
    LoginResponseDto loginUser(LoginRequestDto loginRequestDto);
    UserResponseDto getById(Long id);
}
