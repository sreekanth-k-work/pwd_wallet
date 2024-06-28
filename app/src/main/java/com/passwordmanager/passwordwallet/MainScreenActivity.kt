package com.example.passwordmanager

import PasswordListAdapter
import PasswordViewModel
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.passwordmanager.passwordwallet.R
import java.util.concurrent.TimeUnit

class MainScreenActivity : FragmentActivity() {
    private var passwordViewModel: PasswordViewModel? = null
    private val REQUEST_BIOMETRIC_PERMISSION_CODE = 1000

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_BIOMETRIC_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, initialize and use BiometricPrompt
                    Log.d("MainScreenActivity", "Biometric permission granted")
                } else {
                    // Permission denied, handle accordingly (e.g., show error message)
                    Log.d("MainScreenActivity", "Biometric permission denied")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        Log.d("MainScreenActivity", "Activity created")

        // Check if the USE_BIOMETRIC permission is granted
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC)
            == PackageManager.PERMISSION_GRANTED) {
            Log.d("MainScreenActivity", "Biometric permission already granted")
        } else {
            // Request the USE_BIOMETRIC permission
            ActivityCompat.requestPermissions(
                this as Activity,
                arrayOf(android.Manifest.permission.USE_BIOMETRIC),
                REQUEST_BIOMETRIC_PERMISSION_CODE
            )
        }

        // Schedule the worker
        scheduleAutoLockWorker()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PasswordListAdapter()
        recyclerView.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainScreenActivity, AddDetailsScreenActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
        }

        passwordViewModel = ViewModelProvider(this).get(PasswordViewModel::class.java)

        passwordViewModel?.loadPasswordEntries()

        passwordViewModel?.passwordEntries?.observe(this, Observer { passwordEntries ->
            adapter.setPasswords(passwordEntries)
        })

        hideStatusBar()
    }

    private fun scheduleAutoLockWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val autoLockWorkRequest = OneTimeWorkRequestBuilder<AutoLockWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniqueWork(
            "AutoLockWorker",
            ExistingWorkPolicy.REPLACE,
            autoLockWorkRequest
        )

        Log.d("MainScreenActivity", "AutoLockWorker scheduled")
    }

    private fun hideStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }
}
