package ru.spaceinvasion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SpaceInvasion {
    public static void main(String[] args) {
        SpringApplication.run(SpaceInvasion.class, args);
    }

    @Bean
    @SuppressWarnings("AnonymousInnerClassMayBeStatic")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v1/**")
                        .allowCredentials(true).allowedOrigins(
                        /* Продакш машина */
                        "http://space-invasion.ru",
                        "https://space-invasion.ru",
                        "http://www.space-invasion.ru",
                        "https://www.space-invasion.ru",
                        /* Дев машина */
                        "http://space-invasion-frontend.herokuapp.com",
                        "https://space-invasion-frontend.herokuapp.com",
                        "http://www.space-invasion-frontend.herokuapp.com",
                        "https://www.space-invasion-frontend.herokuapp.com"
                ).allowedMethods("GET", "HEAD", "POST", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }
}
