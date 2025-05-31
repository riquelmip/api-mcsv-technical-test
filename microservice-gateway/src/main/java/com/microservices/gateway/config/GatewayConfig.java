package com.microservices.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {
    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Ruta para el servicio de autenticación (auth-service)
                .route("auth-service", r -> r.path("/api/mcsv-auth/**")
                        .uri("http://localhost:8090"))

//                // Ruta para el servicio de estudiantes (students-service)
//                .route("students-service", r -> r.path("/api/mcsv-students/**")
//                        .filters(f -> f.filter(filter))
//                        .uri("http://localhost:8091"))
//
//                // Ruta para el servicio de cursos (courses-service)
//                .route("courses-service", r -> r.path("/api/mcsv-courses/**")
//                        .filters(f -> f.filter(filter))
//                        .uri("http://localhost:8092"))

                .build();
    }

}
