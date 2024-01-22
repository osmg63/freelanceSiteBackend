package com.example.freelance.demo.start.dto;

public record UserProfileUpdateRequest(
         String username,
         String photoUrl,
         String skills,
         String biyografi,
         String location,
         String school
) {
}
