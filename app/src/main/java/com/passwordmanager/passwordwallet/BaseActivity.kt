package com.example.passwordmanager

import PasswordViewModel
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.passwordmanager.passwordwallet.R

open class BaseActivity : FragmentActivity() {

    private var lastInteractionTime: Long = 0
    private var USER_IDLE_THRESHOLD_TIME  = 5  * 1000

    override fun onUserInteraction() {
        super.onUserInteraction()
        lastInteractionTime = System.currentTimeMillis()
        Log.d("BaseActivity", "User Interacted")
    }

    open fun isUserActive(): Boolean {
        // Check if the user interacted within the last 5 minutes
        val currentTime     =   System.currentTimeMillis()
        if(currentTime - lastInteractionTime < USER_IDLE_THRESHOLD_TIME){
            //user has interacted..
            return true
        }

        return false

    }
}
