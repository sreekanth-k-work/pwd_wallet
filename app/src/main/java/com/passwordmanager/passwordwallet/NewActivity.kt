package com.example.passwordmanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.passwordmanager.passwordwallet.R

class NewActivity : AppCompatActivity() {
    private var websiteEditText: EditText? = null
    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var submitButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        websiteEditText     = findViewById<EditText>(R.id.id_website)
        usernameEditText    = findViewById<EditText>(R.id.id_username)
        passwordEditText    = findViewById<EditText>(R.id.id_password)
        submitButton        = findViewById<Button>(R.id.id_submit_button)

        submitButton?.setOnClickListener(View.OnClickListener {
            val website     = websiteEditText!!.getText().toString().trim { it <= ' ' }
            val username    = usernameEditText!!.getText().toString().trim { it <= ' ' }
            val password    = passwordEditText!!.getText().toString().trim { it <= ' ' }
            if (website.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@NewActivity, "All fields are required", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Handle the submission, e.g., save to database or pass back to previous activity
                Toast.makeText(this@NewActivity, "Submitted!", Toast.LENGTH_SHORT).show()
                // For now, just finish the activity
                finish()
            }
        })
    }
}