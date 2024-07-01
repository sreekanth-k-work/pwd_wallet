package com.example.passwordmanager

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.FragmentActivity

open class BaseActivity : FragmentActivity() {

    private var lastInteractionTime: Long = 0
    public var USER_IDLE_THRESHOLD_TIME  = 30  * 1000 //30 seconds delay.
    public var CHECKING_FOR_USER_ACTIVITY_TIME = 5 *1000

    override fun onUserInteraction() {
        super.onUserInteraction()
        lastInteractionTime = System.currentTimeMillis()
        Log.d("BaseActivity", "User Interacted")
        setLastActiveTime(lastInteractionTime)
    }

    fun isUserActive(): Boolean {
        val lastActiveTime  = getLastActiveTime()
        val currentTime     = System.currentTimeMillis()
        val inactiveThreshold = USER_IDLE_THRESHOLD_TIME // 30 seconds in milliseconds

        val sessionTime:Long  = currentTime - lastActiveTime
        return !(sessionTime >= inactiveThreshold)
    }

    private fun getLastActiveTime(): Long {
        val preferences: SharedPreferences = applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return preferences.getLong("last_active_time", 0)
    }

    private fun setLastActiveTime(iLastActiveTime:Long){
        val preferences: SharedPreferences = applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putLong("last_active_time",iLastActiveTime)
        editor.apply()
    }


}
