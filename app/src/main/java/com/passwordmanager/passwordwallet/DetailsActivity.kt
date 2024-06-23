package com.passwordmanager.passwordwallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordmanager.PasswordEntry

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get the selected PasswordEntry from intent extras
//        val passwordEntry = intent.getParcelableExtra<PasswordEntry>("PASSWORD_ENTRY")

        // Populate details in the UI
//        passwordEntry?.let {
//            websiteTextView.text = it.title
//            usernameTextView.text = it.username
//            passwordTextView.text = it.password
//        }
    }
}
