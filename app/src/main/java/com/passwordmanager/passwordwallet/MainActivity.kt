package com.example.passwordmanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.passwordmanager.passwordwallet.R

class MainActivity : AppCompatActivity() {
    private val viewModel: PasswordViewModel by viewModels()
    private lateinit var passwordAdapter: PasswordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        passwordAdapter = PasswordAdapter(emptyList())
        recyclerView.adapter = passwordAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.passwords.observe(this, Observer { passwords ->
            passwordAdapter.updatePasswords(passwords)
        })

        // Start auto-lock service
        val serviceIntent = Intent(this, AutoLockService::class.java)
        startService(serviceIntent)
    }
}
