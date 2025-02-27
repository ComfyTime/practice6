package org.example.practice6.auth.dto;

import lombok.Getter;

@Getter
public class AuthSignupResponseDto {

    private final Long memberId;
    private final String email;

    public AuthSignupResponseDto(Long memberId, String email) {
        this.memberId = memberId;
        this.email = email;
    }
}