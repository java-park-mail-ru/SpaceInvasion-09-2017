package ru.spaceinvasion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.spaceinvasion.utils.Constants;

@SpringBootApplication
public class SpaceInvasion {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(SpaceInvasion.class);

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
                        .allowCredentials(true)
                        .allowedOrigins(Constants.UrlConstants.ALLOWED_ORIGINS)
                        .allowedMethods("GET", "HEAD", "POST", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }
}
