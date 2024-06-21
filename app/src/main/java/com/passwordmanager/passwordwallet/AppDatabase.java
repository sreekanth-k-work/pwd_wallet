package com.passwordmanager.passwordwallet;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PasswordEntry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PasswordDao passwordDao();
}
