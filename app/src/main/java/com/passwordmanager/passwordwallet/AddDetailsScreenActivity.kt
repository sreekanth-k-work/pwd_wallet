package com.example.passwordmanager

import android.R
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class AddDetailsScreenActivity : AppCompatActivity() {
    private var websiteEditText: EditText? = null
    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var submitButton: Button? = null
    private var showPwdToggle:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.passwordmanager.passwordwallet.R.layout.add_details_screen_layout)

        websiteEditText     = findViewById<EditText>(com.passwordmanager.passwordwallet.R.id.id_website)
        usernameEditText    = findViewById<EditText>(com.passwordmanager.passwordwallet.R.id.id_username)
        passwordEditText    = findViewById<EditText>(com.passwordmanager.passwordwallet.R.id.id_password)
        submitButton        = findViewById<Button>(com.passwordmanager.passwordwallet.R.id.id_submit_button)

        showPwdToggle       = findViewById(com.passwordmanager.passwordwallet.R.id.showPasswordToggle)
        showPwdToggle?.setOnClickListener(View.OnClickListener {
            // Toggle password visibility
            if (passwordEditText!!.getInputType() == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                passwordEditText!!.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            } else {
                passwordEditText!!.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
        })


        submitButton?.setOnClickListener(View.OnClickListener {
            val website     = websiteEditText!!.getText().toString().trim { it <= ' ' }
            val username    = usernameEditText!!.getText().toString().trim { it <= ' ' }
            val password    = passwordEditText!!.getText().toString().trim { it <= ' ' }
            if (website.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@AddDetailsScreenActivity, "All fields are required", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Handle the submission, e.g., save to database or pass back to previous activity
                Toast.makeText(this@AddDetailsScreenActivity, "Submitted!", Toast.LENGTH_SHORT).show()
                // For now, just finish the activity
                finish()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()

//        overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
    }
}