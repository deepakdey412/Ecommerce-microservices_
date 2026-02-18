package com.ecom.user.services.impl;

import com.ecom.user.dto.LoginRequestDto;
import com.ecom.user.dto.LoginResponseDto;
import com.ecom.user.dto.UserCreatedRequestDto;
import com.ecom.user.dto.UserResponseDto;
import com.ecom.user.entity.Users;
import com.ecom.user.repository.UserRepository;
import com.ecom.user.services.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements IUserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto registerUser(UserCreatedRequestDto userCreatedRequestDto) {
        if(userRepository.existsByEmail(userCreatedRequestDto.getEmail())){
            throw new RuntimeException("User already exists");
        }
        Users user = new Users();
        user.setName(userCreatedRequestDto.getName());
        user.setEmail(userCreatedRequestDto.getEmail());
        user.setPassword(userCreatedRequestDto.getPassword());
        Users savedUser = userRepository.save(user);
        return new UserResponseDto();
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {

        return null;
    }

    @Override
    public UserResponseDto getById(Long id) {
        return null;
    }
}
