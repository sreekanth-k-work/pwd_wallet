package com.example.passwordmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.passwordmanager.passwordwallet.R


class NewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.passwordmanager.passwordwallet.R.layout.activity_new)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(com.passwordmanager.passwordwallet.R.anim.no_animation, com.passwordmanager.passwordwallet.R.anim.slide_down)
    }
}
