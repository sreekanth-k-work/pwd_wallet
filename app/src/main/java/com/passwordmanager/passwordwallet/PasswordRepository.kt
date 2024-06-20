package com.example.passwordmanager

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordRepository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun getPasswords(): LiveData<List<Password>> {
        val passwordsLiveData = MutableLiveData<List<Password>>()
        CoroutineScope(Dispatchers.IO).launch {
            val passwords = dbHelper.getPasswords()
            passwordsLiveData.postValue(passwords)
        }
        return passwordsLiveData
    }

    fun addPassword(url: String, username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val encryptedPassword = EncryptionHelper.encrypt(password)
                dbHelper.addPassword(url, username, encryptedPassword)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePassword(id: Int, url: String, username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val encryptedPassword = EncryptionHelper.encrypt(password)
                dbHelper.updatePassword(id, url, username, encryptedPassword)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deletePassword(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dbHelper.deletePassword(id)
        }
    }
}
