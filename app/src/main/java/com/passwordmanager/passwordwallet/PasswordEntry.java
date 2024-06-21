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

    // Constructor to initialize the fields (excluding auto-generated id)
    public PasswordEntry(String website, String username, String encryptedPassword) {
        this.website = website;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    // Optional: Add getter and setter methods if needed
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
