package com.example.myapp

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.passwordmanager.passwordwallet.R

class PasswordActivity : AppCompatActivity() {
    private var mEtCurrentPassword: EditText? = null
    private var mEtNewPassword: EditText? = null
    private var mEtConfirmPassword: EditText? = null
    private var mBtnSetPassword: Button? = null
    private var mSharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        mEtCurrentPassword      =   findViewById(R.id.et_current_password)
        mEtNewPassword          =   findViewById(R.id.et_new_password)
        mEtConfirmPassword      =   findViewById(R.id.et_confirm_password)
        mBtnSetPassword         =   findViewById(R.id.btn_set_password)

        mSharedPreferences      =   getSharedPreferences("MyAppPrefs", MODE_PRIVATE)

        val currentPassword     =   mSharedPreferences?.getString("password", null)
        if (currentPassword != null) {
            mEtCurrentPassword?.visibility = View.VISIBLE
        } else {
            // No password exists, show other screens or handle accordingly
            // For example:
            // startActivity(Intent(this, OtherActivity::class.java))
            // finish()
        }

        mBtnSetPassword?.setOnClickListener {
            if (currentPassword != null) {
                handlePasswordAuthentication(currentPassword)
            } else {
                handleSetNewPassword()
            }
        }
    }

    private fun handlePasswordAuthentication(currentPassword: String) {
        val inputCurrentPassword = mEtCurrentPassword!!.text.toString()
        if (TextUtils.isEmpty(inputCurrentPassword) || inputCurrentPassword != currentPassword) {
            Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show()
            return
        }
        // Password authentication successful, proceed to other screens or activities
        // For example:
        // startActivity(Intent(this, OtherActivity::class.java))
        // finish()
    }

    private fun handleSetNewPassword() {
        val newPassword = mEtNewPassword!!.text.toString()
        val confirmPassword = mEtConfirmPassword!!.text.toString()

        if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please enter both new password and confirm password", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Save the new password in SharedPreferences
        mSharedPreferences!!.edit().putString("password", newPassword).apply()
        Toast.makeText(this, "Password set successfully", Toast.LENGTH_SHORT).show()

        // Proceed to other screens or activities
        // For example:
        // startActivity(Intent(this, OtherActivity::class.java))
        // finish()
    }
}
