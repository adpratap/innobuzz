package com.noreplypratap.innobuzz.accessibility

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.noreplypratap.innobuzz.utils.Constants
import com.noreplypratap.innobuzz.utils.Utils.status

class MyAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.packageName == "com.whatsapp" && event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            // WhatsApp is opened
            status = true
            //Toast.makeText(applicationContext, "WhatsApp is opened", Toast.LENGTH_SHORT).show()
            Log.d(Constants.TAG,"WhatsApp is opened!!............")
        }
    }
    override fun onInterrupt() {
        // Do nothing
    }
}
