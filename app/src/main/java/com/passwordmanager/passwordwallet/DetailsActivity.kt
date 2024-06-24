package com.passwordmanager.passwordwallet

import android.graphics.Paint
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordmanager.PasswordEntry

class DetailActivity : AppCompatActivity() {

    private var websiteTextView:TextView?   = null
    private var usernameTextView:TextView?  = null
    private var passwordTextView:TextView?  = null
    private  var eyeIconIv: ImageView?      = null
    private var backButton:ImageView?       = null
    private var isPasswordVisible           = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        websiteTextView                = findViewById(R.id.websiteValueTextView)
        usernameTextView               = findViewById(R.id.usernameValueTextView)
        passwordTextView               = findViewById(R.id.passwordValueTextView)
        eyeIconIv                      = findViewById(R.id.id_eye_icon)


        backButton                     = findViewById(R.id.id_back_btn)

        backButton?.setOnClickListener(View.OnClickListener {
            finish()
            overridePendingTransition(0, R.anim.slide_down)
        })

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

        eyeIconIv?.setOnClickListener {

            // Move the cursor to the end of the text
            updatePasswordStatus()
        }

        //Just to set initial condition...
        isPasswordVisible = true
        updatePasswordStatus()
    }

    fun updatePasswordStatus(){
        if (isPasswordVisible) {
            passwordTextView!!.transformationMethod = PasswordTransformationMethod.getInstance()
            eyeIconIv!!.setImageResource(R.drawable.ic_eye_closed)
        } else {
            passwordTextView!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
            eyeIconIv!!.setImageResource(R.drawable.ic_eye_open)
        }
        isPasswordVisible = !isPasswordVisible
    }


    override fun finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_static, R.anim.slide_out_right);
    }
}
