package com.passwordmanager.passwordwallet;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "passwords")
public class PasswordEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String website;
    public String username;
    public String encryptedPassword;
}
