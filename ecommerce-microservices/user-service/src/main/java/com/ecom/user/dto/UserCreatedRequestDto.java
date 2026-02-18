package com.ecom.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedRequestDto {
    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Password required")
    @Size(min = 6 , message = "Password must be length 6")
    private String password;

    @NotBlank(message = "Email required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Phone number required")
    private String phone;
}

