package com.noreplypratap.innobuzz.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.noreplypratap.innobuzz.utils.Constants

class MyAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {

        val packageNames : String = event.packageName.toString()

        if (packageNames == "com.whatsapp"){
            Constants.status = true
        }

    }

    override fun onInterrupt() {
        Log.d(Constants.TAG,"onInterrupt Error ...... ")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        val info : AccessibilityServiceInfo? = null

        info?.apply {

            eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED or AccessibilityEvent.TYPE_VIEW_FOCUSED

            feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN

            notificationTimeout = 2000
        }

        this.serviceInfo = info

        Log.d(Constants.TAG,"onServiceConnected")

    }
}