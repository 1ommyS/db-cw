package ru.mai.coursework.infrastructure.configuration.security.providers

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtTokenProvider {
    companion object {
        private val jwtSecret: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
        private val jwtRefreshSecret: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

        private const val ExpirationMs = 3600000 // 1 час
        const val RefreshExpirationMs = 604800000 // 7 дней
    }

    fun generateToken(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + ExpirationMs)

        return Jwts
            .builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(jwtSecret)
            .compact()
    }

    fun generateRefreshToken(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + RefreshExpirationMs)

        return Jwts
            .builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(jwtRefreshSecret)
            .compact()
    }

    fun getUsernameFromJWT(token: String): String {
        val claims =
            Jwts
                .parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .body
        return claims.subject
    }

    fun getUsernameFromRefreshToken(refreshToken: String): String {
        val claims =
            Jwts
                .parserBuilder()
                .setSigningKey(jwtRefreshSecret)
                .build()
                .parseClaimsJws(refreshToken)
                .body
        return claims.subject
    }

    fun validateToken(token: String): Boolean =
        try {
            Jwts
                .parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
            true
        } catch (ex: Exception) {
            false
        }

    fun validateRefreshToken(refreshToken: String): Boolean =
        try {
            Jwts
                .parserBuilder()
                .setSigningKey(jwtRefreshSecret)
                .build()
                .parseClaimsJws(refreshToken)
            true
        } catch (ex: Exception) {
            false
        }
}
