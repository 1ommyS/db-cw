package ru.mai.coursework.entity

import org.springframework.security.core.GrantedAuthority

data class Role (
    val id: Int,
    val name: String
) : GrantedAuthority{
    override fun getAuthority(): String? = name
}