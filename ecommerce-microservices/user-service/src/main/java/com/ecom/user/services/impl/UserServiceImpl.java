package com.ecom.user.services.impl;

import com.ecom.user.dto.LoginRequestDto;
import com.ecom.user.dto.LoginResponseDto;
import com.ecom.user.dto.UserCreatedRequestDto;
import com.ecom.user.dto.UserResponseDto;
import com.ecom.user.entity.Users;
import com.ecom.user.exception.ResourceAlreadyExistException;
import com.ecom.user.exception.ResourceNotFoundException;
import com.ecom.user.mapper.AuthMapper;
import com.ecom.user.mapper.UserMapper;
import com.ecom.user.repository.UserRepository;
import com.ecom.user.services.IUserService;
import com.ecom.user.util.JwtUtil;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, AuthMapper authMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authMapper = authMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponseDto registerUser(UserCreatedRequestDto userCreatedRequestDto) {
        if(userRepository.existsByEmail(userCreatedRequestDto.getEmail())){
            throw new ResourceAlreadyExistException("User already exists with this email : " + userCreatedRequestDto.getEmail());
        }
        Users newUser = userMapper.toEntity(userCreatedRequestDto);
        Users savedUser = userRepository.save(newUser);

        return userMapper.toDto(savedUser);
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {

        Users foundUser = userRepository
                .findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean matches = passwordEncoder
                .matches(loginRequestDto.getPassword(), foundUser.getPassword());

        if (!matches) {
            throw new ResourceNotFoundException("Wrong password");
        }

        String token = jwtUtil.generateToken(
                foundUser.getId(),
                foundUser.getEmail(),
                foundUser.getRole()
        );

        return authMapper.toLoginResponse(foundUser, token);
    }


    @Override
    public UserResponseDto getById(Long id) {

        Users user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        return userMapper.toDto(user);
    }

}
