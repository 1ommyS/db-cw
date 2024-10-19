package ru.mai.coursework.infrastructure.configuration.security.filters

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CustomCorsFilter {
    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration();
        config.allowCredentials = true;
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        val source = UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return CorsFilter(source);
    }
}