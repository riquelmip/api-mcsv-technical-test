package com.microservices.auth.model.dto;
import lombok.Data;

@Data
public class AuthLoginGoogleDTO {
    private String provider;
    private String id;
    private String email;
    private String name;
    private String photoUrl;
    private String firstName;
    private String lastName;
    private String authToken;
    private String idToken;
    private String authorizationCode;
    private Object response;

}


