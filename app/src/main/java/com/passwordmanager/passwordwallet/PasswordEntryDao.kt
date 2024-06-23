package com.passwordmanager.passwordwallet;


import PasswordEntry

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PasswordEntryDao {
    @Query("SELECT * FROM password_entries")
    suspend fun getAll(): List<PasswordEntry>

    @Insert
    suspend fun insert(passwordEntry: PasswordEntry)
}