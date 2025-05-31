package com.microservices.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "token"})
public record ExtractUsernameFromTokenResponse(
        String username,
        String token
) {
}