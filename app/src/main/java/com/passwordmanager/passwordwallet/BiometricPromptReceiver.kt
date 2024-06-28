package com.example.passwordmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.passwordmanager.passwordwallet.R

class BiometricPromptService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("BiometricPromptService", "Service started")
        if (intent != null && intent.action == "SHOW_BIOMETRIC_PROMPT") {
            showBiometricPrompt()
        }
        return START_NOT_STICKY
    }

    private fun showBiometricPrompt() {
        Log.d("BiometricPromptService", "Showing biometric prompt")
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(
            getActivityInstance(),
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.d("BiometricPromptService", "Authentication error: $errString")
                    showPasswordPrompt()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d("BiometricPromptService", "Authentication succeeded")
                    stopSelf()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.d("BiometricPromptService", "Authentication failed")
                    showPasswordPrompt()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("App Lock")
            .setDescription("Use fingerprint or face ID to unlock")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun showPasswordPrompt() {
        Log.d("BiometricPromptService", "Showing password prompt")
        // Implement your own password prompt or dialog here
        // Once user enters password successfully, clear app activity
        stopSelf()
    }

    private fun getActivityInstance(): FragmentActivity {
        // Return your activity instance here. This could be achieved by maintaining a weak reference
        // to the current activity in your application class or other means.
        throw NotImplementedError("Provide implementation to get current activity instance")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("BiometricPromptService", "Service created")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        startForeground(NOTIFICATION_ID, getNotification())
    }

    private fun getNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Biometric Prompt Service")
            .setContentText("Biometric authentication is active")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Biometric Prompt Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    companion object {
        private const val CHANNEL_ID = "BiometricPromptServiceChannel"
        private const val NOTIFICATION_ID = 1
    }
}
