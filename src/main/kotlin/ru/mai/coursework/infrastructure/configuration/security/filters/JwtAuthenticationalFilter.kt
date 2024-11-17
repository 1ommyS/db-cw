package ru.mai.coursework.infrastructure.configuration.security.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.mai.coursework.infrastructure.configuration.security.providers.JwtTokenProvider
import java.util.logging.Logger

@Component
class JwtAuthenticationFilter(
    private val tokenProvider: JwtTokenProvider,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    private val logger: Logger = Logger.getLogger(JwtAuthenticationFilter::class.java.name)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = getJwtFromRequest(request)
            if (jwt != null && tokenProvider.validateToken(jwt)) {
                setAuthenticationContext(jwt, request)
            }
        } catch (ex: Exception) {
            logger.warn("Не удалось установить аутентификацию: ${ex.message}")
        }

        filterChain.doFilter(request, response)
    }


    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    private fun setAuthenticationContext(jwt: String, request: HttpServletRequest) {
        val username = tokenProvider.getUsernameFromJWT(jwt)
        val userDetails = userDetailsService.loadUserByUsername(username)

        if (userDetails != null) {
            val authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }
    }
}