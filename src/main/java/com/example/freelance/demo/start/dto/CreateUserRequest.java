package com.example.freelance.demo.start.dto;

import com.example.freelance.demo.start.entitiy.Role;
import lombok.Builder;

import java.util.Set;

@Builder
public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        String username,
        String password,
        String location,
        int wallet,
        int age,
        String identityNumber,
        Set<Role> authorities

) {
}
