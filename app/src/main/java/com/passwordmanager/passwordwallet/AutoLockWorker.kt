package com.example.passwordmanager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class AutoLockWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("AutoLockWorker", "Worker running")
        // Check for user inactivity and start the service if needed
        if (isUserInactive()) {
            Log.d("AutoLockWorker", "User inactive, starting biometric prompt service")
            startBiometricPromptService()
        } else {
            Log.d("AutoLockWorker", "User active, no action needed")
        }

        // Reschedule the worker to run again after 10 seconds
        val autoLockWorkRequest = OneTimeWorkRequestBuilder<AutoLockWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(autoLockWorkRequest)

        return Result.success()
    }

    private fun startBiometricPromptService() {
        val intent = Intent(applicationContext, BiometricPromptService::class.java)
        intent.action = "SHOW_BIOMETRIC_PROMPT"
        ContextCompat.startForegroundService(applicationContext, intent)
    }

    private fun isUserInactive(): Boolean {
        val lastActiveTime  = getLastActiveTime()
        val currentTime     = System.currentTimeMillis()
        val inactiveThreshold = 10 * 1000 // 10 seconds in milliseconds

        return (currentTime - lastActiveTime) >= inactiveThreshold
    }

    private fun getLastActiveTime(): Long {
        val preferences: SharedPreferences = applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return preferences.getLong("last_active_time", System.currentTimeMillis())
    }
}
