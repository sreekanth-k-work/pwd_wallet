package com.passwordmanager.passwordwallet

import android.graphics.Paint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordmanager.PasswordEntry

class DetailActivity : AppCompatActivity() {

    private var websiteTextView:TextView?   = null
    private var usernameTextView:TextView?  = null
    private var passwordTextView:TextView?  = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        websiteTextView                = findViewById(R.id.websiteValueTextView)
        usernameTextView               = findViewById(R.id.usernameValueTextView)
        passwordTextView               = findViewById(R.id.passwordValueTextView)

        var detailsHeadingTv:TextView  = findViewById(R.id.id_details_heading_tv)
        detailsHeadingTv.paintFlags    = Paint.UNDERLINE_TEXT_FLAG or detailsHeadingTv.paintFlags

        // Get the selected PasswordEntry from intent extras
        val passwordEntry = intent.getSerializableExtra("PASSWORD_ENTRY") as? PasswordEntry

//         Populate details in the UI
        passwordEntry?.let {
            websiteTextView?.text    = it.title
            usernameTextView?.text   = it.username
            passwordTextView?.text   = it.password
        }
    }


    override fun finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_static, R.anim.slide_out_right);
    }
}
