package com.microservices.auth.model.dto;

import com.microservices.auth.model.ProviderEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record AuthCreateUserRequestDTO(
        @NotBlank String username,
        @NotBlank String password,
        String name,
        String email,
        boolean isOAuth2,
        ProviderEnum provider,
        @Valid AuthCreateRoleRequestDTO roles
) {
}
