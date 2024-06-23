package com.example.passwordmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_entries")
data class PasswordEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val username: String,
    val password: String
) {
    // Secondary constructor that excludes id
    constructor(title: String, username: String, password: String) : this(
        id = 0,
        title = title,
        username = username,
        password = password
    )
}
