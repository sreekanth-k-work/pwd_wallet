package com.example.passwordmanager

data class Password(
    val id: Int,
    val url: String,
    val username: String,
    val encryptedPassword: ByteArray
)
