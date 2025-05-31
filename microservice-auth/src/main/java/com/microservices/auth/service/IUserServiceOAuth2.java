package com.microservices.auth.service;



import com.microservices.auth.model.ProviderEnum;
import com.microservices.auth.model.UserEntity;

import java.util.List;

public interface IUserServiceOAuth2 {
    UserEntity getUserByUsername(String username);
    UserEntity getUserByUsernameAndProvider(String username, ProviderEnum provider);

}
