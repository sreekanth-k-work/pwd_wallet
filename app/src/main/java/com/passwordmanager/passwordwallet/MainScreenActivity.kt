package com.example.passwordmanager

import PasswordListAdapter
import PasswordViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.passwordmanager.passwordwallet.R

class MainScreenActivity : AppCompatActivity() {
    private lateinit var passwordViewModel: PasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PasswordListAdapter()
        recyclerView.adapter = adapter

        passwordViewModel = ViewModelProvider(this).get(PasswordViewModel::class.java)
//        passwordViewModel.allPasswords.observe(this, Observer { passwords ->
//            passwords?.let { adapter.setPasswords(it) }
//        })
    }
}
