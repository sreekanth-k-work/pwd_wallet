package com.passwordmanager.passwordwallet;

//import static androidx.compose.ui.text.input.CursorAnchorInfoBuilder_androidKt.build;

import PasswordEntry
import androidx.room.Database;
import androidx.room.RoomDatabase;

import androidx.room.Room;
import android.content.Context;

import kotlin.jvm.Volatile;

@Database(entities = [PasswordEntry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun passwordEntryDao(): PasswordEntryDao

    companion object {
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "password_db").build()

    }
}
