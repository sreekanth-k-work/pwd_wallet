package com.example.passwordmanager

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.passwordmanager.passwordwallet.R
import java.util.concurrent.Executor

class BottomSheetFragment : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "MyBottomSheetFragment"
    }

    @org.jetbrains.annotations.Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        var inflatedView:View =  inflater.inflate(R.layout.bottom_sheet_dialog, container, false)
        inflatedView.findViewById<Button>(R.id.id_use_biometric_btn).setOnClickListener {
            Log.d("BottomSheetFragment","Biometric button clicked")
            Toast.makeText(inflatedView.context,"Biometric button clicked.", Toast.LENGTH_SHORT).show()
        }

        inflatedView.findViewById<Button>(R.id.id_use_password_btn).setOnClickListener {
            Log.d("BottomSheetFragment","Password button clicked")
            Toast.makeText(inflatedView.context, "Password authentication selected", Toast.LENGTH_SHORT).show()
        }
        return inflatedView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        // Inflate your custom layout here, or modify the existing one
        val view: View = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog, null)



        context?.let {
            setUpBiometric(it, view)
        }
        // Set the content view and return the dialog
        dialog.setContentView(view)
        return dialog
    }



    fun setUpBiometric(context: Context, view: View) {
        val executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(context, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(context, "Authentication succeeded!", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()


    }

}
