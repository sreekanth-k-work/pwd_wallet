package com.example.passwordmanager

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.passwordmanager.passwordwallet.R
import java.util.concurrent.Executor

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mBiometricPrompt: BiometricPrompt
    private lateinit var mPromptInfo: BiometricPrompt.PromptInfo
    private lateinit var mBiometricManager: BiometricManager
    private lateinit var enrollBiometricRequestLauncher: ActivityResultLauncher<Intent>
    private val authenticators = BIOMETRIC_STRONG or DEVICE_CREDENTIAL


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
            tryAuthenticateBiometric()
        }

        inflatedView.findViewById<Button>(R.id.id_use_password_btn).setOnClickListener {
            Log.d("BottomSheetFragment","Password button clicked")
            Toast.makeText(inflatedView.context, "Password authentication selected", Toast.LENGTH_SHORT).show()
        }

        setUpBiometric(inflatedView.context)
        return inflatedView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        // Inflate your custom layout here, or modify the existing one
        val view: View = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog, null)

        context?.let {
//            setUpBiometric(it, view)
        }
        // Set the content view and return the dialog
        dialog.setContentView(view)
        return dialog
    }

    /**
     * Attempt to show biometric prompt dialog to user.
     */
    private fun tryAuthenticateBiometric() {
        checkDeviceCapability {
            mBiometricPrompt.authenticate(mPromptInfo)
        }
    }

    /**
     * Check the device capability for biometric.
     *
     * If the device is capable, [onSuccess] will be called. Otherwise, a [Snackbar] is shown.
     */
    private fun checkDeviceCapability(onSuccess: () -> Unit) {
        when (mBiometricManager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS, BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                onSuccess()
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                showSnackBar("No biometric features available on this device")
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                showSnackBar("Biometric features are currently unavailable")
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                showSnackBar("Biometric options are incompatible with the current Android version")
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    enrollBiometricRequestLauncher.launch(
                        Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                            putExtra(
                                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                authenticators
                            )
                        }
                    )
                } else {
                    showSnackBar("Could not request biometric enrollment in API level < 30")
                }
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                showSnackBar("Biometric features are unavailable because security vulnerabilities has been discovered in one or more hardware sensors")
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }



    fun setUpBiometric(context: Context) {
        // Initialize BiometricManager for checking
        mBiometricManager = BiometricManager.from(context)

        // Initialize BiometricPrompt to setup success & error callbacks of biometric prompt
        var executor: Executor = ContextCompat.getMainExecutor(context)
        mBiometricPrompt =
            BiometricPrompt(
                this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(
                            context,
                            "Authentication error: Code: $errorCode ($errString)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(
                            context,
                            "Failed to Authenticate, try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        val type = result.authenticationType
                        Toast.makeText(context, "Authentication successful!", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
            )

        // Initialize PromptInfo to set title, subtitle, and authenticators of the biometric prompt
        try {
            mPromptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Example biometric authentication")
                .setSubtitle("Please authenticate yourself first.")
                .setAllowedAuthenticators(authenticators)
                .build()
        } catch (e: Exception) {
            Toast.makeText(
                context,
                e.message ?: "Unable to initialize PromptInfo",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Initialize a launcher for requesting user to enroll in biometric
        enrollBiometricRequestLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    tryAuthenticateBiometric()
                } else {
                    showSnackBar("Failed to enroll in biometric")
                }
            }
    }

    private fun showSnackBar(message: String) {
        Toast.makeText(context, message, Snackbar.LENGTH_SHORT).show()

    }
}
