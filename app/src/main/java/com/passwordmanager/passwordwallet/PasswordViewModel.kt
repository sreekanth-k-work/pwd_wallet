package com.example.passwordmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class PasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PasswordRepository(application)
    val passwords: LiveData<List<Password>> = repository.getPasswords()

    fun addPassword(url: String, username: String, password: String) {
        repository.addPassword(url, username, password)
    }

    fun updatePassword(id: Int, url: String, username: String, password: String) {
        repository.updatePassword(id, url, username, password)
    }

    fun deletePassword(id: Int) {
        repository.deletePassword(id)
    }
}
