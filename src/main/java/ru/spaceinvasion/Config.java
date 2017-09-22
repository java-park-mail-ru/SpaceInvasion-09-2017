package ru.spaceinvasion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class Config {
    @Bean
    @SuppressWarnings("AnonymousInnerClassMayBeStatic")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v1/**")
                    .allowCredentials(true).allowedOrigins(
                        "http://space-invasion.ru",
                        "http://space-invasion.herokuapp.com",
                        "http://www.space-invasion.ru",
                        "http://www.space-invasion.herokuapp.com",
                        "https://space-invasion.ru",
                        "https://space-invasion.herokuapp.com",
                        "https://www.space-invasion.ru",
                        "https://www.space-invasion.herokuapp.com"
                ).allowedMethods("GET", "HEAD", "POST", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }
}
