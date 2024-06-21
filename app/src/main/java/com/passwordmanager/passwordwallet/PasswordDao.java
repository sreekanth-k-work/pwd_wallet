package com.passwordmanager.passwordwallet;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.passwordmanager.passwordwallet.PasswordEntry;

import java.util.List;

@Dao
public interface PasswordDao {
    @Insert
    void insert(PasswordEntry passwordEntry);

    @Query("SELECT * FROM passwords")
    List<PasswordEntry> getAllPasswords();
}
