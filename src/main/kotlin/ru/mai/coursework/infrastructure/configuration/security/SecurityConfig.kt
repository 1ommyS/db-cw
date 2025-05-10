package ru.mai.coursework.infrastructure.configuration.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.session.SessionManagementFilter
import org.springframework.web.filter.CorsFilter
import ru.mai.coursework.infrastructure.configuration.security.filters.JwtAuthenticationFilter

@Configuration
class SecurityConfig(
    private val corsFilter: CorsFilter,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {
    companion object {
        val WHITELIST = arrayOf("/**")
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .addFilterBefore(corsFilter, SessionManagementFilter::class.java)
            .csrf { it.disable() }
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers(*WHITELIST)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
