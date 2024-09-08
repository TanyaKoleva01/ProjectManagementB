package com.example.projectManagement.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешава заявки към всички endpoint-и
                .allowedOrigins("http://localhost:5173") // Задай URL-a на твоето frontend приложение
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // Разрешени HTTP методи
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
