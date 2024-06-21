package com.example.passwordmanager

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME         = "passwordManager.db"
        private const val DATABASE_VERSION      = 1
        private const val TABLE_NAME            = "passwords"
        private const val COLUMN_ID             = "id"
        private const val COLUMN_URL            = "url"
        private const val COLUMN_USERNAME       = "username"
        private const val COLUMN_PASSWORD       = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_URL TEXT,"
                + "$COLUMN_USERNAME TEXT,"
                + "$COLUMN_PASSWORD BLOB)")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addPassword(url: String, username: String, encryptedPassword: ByteArray) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_URL, url)
        values.put(COLUMN_USERNAME, username)
        values.put(COLUMN_PASSWORD, encryptedPassword)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updatePassword(id: Int, url: String, username: String, encryptedPassword: ByteArray) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_URL, url)
        values.put(COLUMN_USERNAME, username)
        values.put(COLUMN_PASSWORD, encryptedPassword)
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun deletePassword(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun getPasswords(): List<Password> {
        val passwords = mutableListOf<Password>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val id          = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val url         = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL))
                val username    = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
                val encryptedPassword = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
                passwords.add(Password(id, url, username, encryptedPassword))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return passwords
    }
}
