package com.ecom.user.mapper;

import com.ecom.user.dto.LoginRequestDto;
import com.ecom.user.dto.LoginResponseDto;
import com.ecom.user.entity.Users;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public LoginResponseDto toLoginResponse(Users user , String token) {
        return new LoginResponseDto(token , "Bearer" , user.getId(), user.getEmail(), user.getName());
    }
}
