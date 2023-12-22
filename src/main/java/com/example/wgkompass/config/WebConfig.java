package com.example.wgkompass.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String[] ALLOWED_ORIGINS = {
            "http://localhost:3000", // Local development
            "https://wg-kompass-static-frontend.azurestaticapps.net", // Azure Static Web App domain
            "https://wgkompass.aedigital.ch" // Custom domain
    };

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // allows all endpoints
                        .allowedOrigins(ALLOWED_ORIGINS) // allows cross-origin from specified locations
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // allowed methods
                        .allowedHeaders("*") // allows all headers
                        .allowCredentials(true); // allows credentials
            }
        };
    }
}
