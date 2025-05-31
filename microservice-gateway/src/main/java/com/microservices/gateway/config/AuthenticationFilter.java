package com.microservices.gateway.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.gateway.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {
    Logger logger = Logger.getLogger(JwtUtils.class.getName());
    @Autowired
    private RouterValidator validator;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //logger.info("Executing filter");
        ServerHttpRequest request = exchange.getRequest();

        if (validator.isSecured.test(request)) {
            if (authMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String authorizationHeader = request.getHeaders().getFirst("Authorization");

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String token = authorizationHeader.substring(7); // Remover "Bearer " del token

            logger.info("Token: " + token);
            try {
                if (jwtUtils.isExpired(token)) {
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }
            } catch (JWTVerificationException e) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

        }
        return chain.filter(exchange);
    }


    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        ResponseObject responseObject = new ResponseObject(false, httpStatus, "You are not authorized to access this resource", null);

        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // Serializar el ResponseObject a JSON
        byte[] bytes = new byte[0]; // Serializar a byte[]
        try {
            bytes = new ObjectMapper().writeValueAsBytes(responseObject); // Asegúrate de importar y configurar ObjectMapper
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Manejo básico de errores
            logger.severe("Error al serializar el objeto ResponseObject a JSON" + e.getMessage());
        }

        // Escribir la respuesta
        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
