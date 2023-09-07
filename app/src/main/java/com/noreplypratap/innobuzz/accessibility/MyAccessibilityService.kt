package com.noreplypratap.innobuzz.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import com.noreplypratap.innobuzz.service.TrackerService
import com.noreplypratap.innobuzz.utils.Constants.DataKeyID
import com.noreplypratap.innobuzz.utils.Constants.PackageName
import com.noreplypratap.innobuzz.utils.Constants.ToastMessage
import com.noreplypratap.innobuzz.utils.logger

class MyAccessibilityService : AccessibilityService() {
    private var lastTimeDelay: Long = 0L
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.packageName == PackageName && event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            logger(ToastMessage)
            val currentTimeMillis = System.currentTimeMillis()
            if ((currentTimeMillis - lastTimeDelay) > 3000L) {
                lastTimeDelay = currentTimeMillis
                sendDataToService()
            }

        }
    }

    private fun sendDataToService() {
        val serviceIntent = Intent(this, TrackerService::class.java)
        serviceIntent.putExtra(DataKeyID, ToastMessage)
        startService(serviceIntent)
    }

    override fun onInterrupt() {
        logger("onInterrupt!!")
    }

}
