package com.example.passwordmanager

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.passwordmanager.passwordwallet.R

class AutoLockService : Service() {
    private val AUTO_LOCK_TIMEOUT = 300000L // 5 minutes
    private val handler = Handler()
    private val autoLockRunnable = Runnable {
        lockApp()
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(1, createNotification())
        resetAutoLockTimer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        resetAutoLockTimer()
        return START_STICKY
    }

    private fun resetAutoLockTimer() {
        handler.removeCallbacks(autoLockRunnable)
        handler.postDelayed(autoLockRunnable, AUTO_LOCK_TIMEOUT)
    }

    private fun lockApp() {
        // Logic to lock the app (e.g., navigate to lock screen)
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, "AUTO_LOCK_CHANNEL")
            .setContentTitle("Password Manager")
            .setContentText("The app will lock after 5 minutes of inactivity.")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        handler.removeCallbacks(autoLockRunnable)
        super.onDestroy()
    }
}
