package com.microservices.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "status", "jwt"})
public record AuthResponseDTO(
        String username,
        String message,
        String jwt,
        Boolean status) {
}
