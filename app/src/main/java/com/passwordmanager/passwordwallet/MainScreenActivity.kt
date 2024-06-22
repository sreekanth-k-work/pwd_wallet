package com.example.passwordmanager

import PasswordListAdapter
import PasswordViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainScreenActivity : AppCompatActivity() {
    private var passwordViewModel: PasswordViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.passwordmanager.passwordwallet.R.layout.activity_main_screen)

        val recyclerView            =   findViewById<RecyclerView>(com.passwordmanager.passwordwallet.R.id.recyclerView)
        recyclerView.layoutManager  =   LinearLayoutManager(this)
        var adapter                 =   PasswordListAdapter()
        recyclerView.adapter        =   adapter

        val fab                     =   findViewById<FloatingActionButton>(com.passwordmanager.passwordwallet.R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(
                this@MainScreenActivity,
                AddDetailsScreenActivity::class.java
            )
            startActivity(intent)
            overridePendingTransition(com.passwordmanager.passwordwallet.R.anim.slide_in_up, com.passwordmanager.passwordwallet.R.anim.stay)
        }

        passwordViewModel           =   ViewModelProvider(this).get(PasswordViewModel::class.java)

        passwordViewModel?.loadPasswordEntries()

        passwordViewModel?.passwordEntries?.observe(this, Observer { passwordEntries ->
            // Update the UI when passwordEntries changes
            adapter = recyclerView.adapter as PasswordListAdapter
            adapter.setPasswords(passwordEntries)
        })
    }
}
