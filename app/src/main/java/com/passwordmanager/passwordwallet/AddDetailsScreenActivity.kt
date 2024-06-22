package com.example.passwordmanager

import PasswordViewModel
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.passwordmanager.passwordwallet.PasswordEntry
import com.passwordmanager.passwordwallet.R

class AddDetailsScreenActivity : AppCompatActivity() {

    private lateinit var passwordViewModel: PasswordViewModel
    private lateinit var websiteEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var eyeIcon: ImageView
    private lateinit var submitButton: Button
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_details_screen_layout)

        passwordViewModel   =   ViewModelProvider(this).get(PasswordViewModel::class.java)
        websiteEditText     =   findViewById(R.id.id_website)
        usernameEditText    =   findViewById(R.id.id_username)
        passwordEditText    =   findViewById(R.id.id_password)
        eyeIcon             =   findViewById(R.id.id_eye_icon)
        submitButton        =   findViewById(R.id.id_submit_button)

        eyeIcon.setOnClickListener {
            if (isPasswordVisible) {
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                eyeIcon.setImageResource(R.drawable.ic_eye_closed)
            } else {
                passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                eyeIcon.setImageResource(R.drawable.ic_eye_open)
            }
            isPasswordVisible = !isPasswordVisible
            // Move the cursor to the end of the text
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        submitButton.setOnClickListener {
            val website     = websiteEditText.text.toString().trim()
            val username    = usernameEditText.text.toString().trim()
            val password    = passwordEditText.text.toString().trim()

            if (website.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                val passwordEntry = PasswordEntry(website, username, password)
//                passwordViewModel.insert(passwordEntry)
                Toast.makeText(this, "Details saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
