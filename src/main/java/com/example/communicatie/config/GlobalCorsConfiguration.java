package com.example.communicatie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
De CORS configuratie is belangrijk om met de frontend te kunnen communiceren
wanneer de frontend op een andere poort opereert dan de backend.
CORS staat voor Cross-Origin Resource Sharing en is een security implementatie
om te bepalen vanaf welke specifieke domeinen, protocollen of poorten deze applicatie benaderd mag worden.
Voor de context van deze opleiding is het voldoende om de CORS instellingen over te nemen zoals hier uitgewerkt..
 */
@Configuration
public class GlobalCorsConfiguration
{
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }
}