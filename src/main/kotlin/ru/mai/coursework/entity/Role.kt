package ru.mai.coursework.entity

import org.springframework.security.core.GrantedAuthority
import java.sql.ResultSet

data class Role(
    val id: Int,
    val name: String
) : GrantedAuthority {
    override fun getAuthority(): String? = name


    fun ResultSet.toRole(): Role {
        return Role(
            id = getInt("id"),
            name = getString("role_name")
        )
    }
}