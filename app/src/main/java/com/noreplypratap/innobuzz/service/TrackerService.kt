package com.noreplypratap.innobuzz.service

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.noreplypratap.innobuzz.R
import com.noreplypratap.innobuzz.notification.buildNotification
import com.noreplypratap.innobuzz.utils.Constants.DataKeyID
import com.noreplypratap.innobuzz.utils.Constants.NOTIFICATION_ID
import com.noreplypratap.innobuzz.utils.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackerService : Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private lateinit var notificationManager: NotificationManager

    private var counter: Int = 0

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        createNotification()
    }

    private fun createNotification() {
        scope.launch {
            val notification = buildAndScheduleNotification()
            withContext(Dispatchers.Main) {
                startForeground(NOTIFICATION_ID, notification)
            }
        }
    }


    private fun updateNotification() {
        scope.launch {
            val notification = buildAndScheduleNotification()
            withContext(Dispatchers.Main) {
                notificationManager.notify(NOTIFICATION_ID, notification)
            }
        }
    }

    private fun showToastMsg(msg: String) {
        scope.launch {
            withContext(Dispatchers.Main) {
                this@TrackerService.showToast(msg)
            }
        }
    }

    private fun Context.buildAndScheduleNotification(): Notification {
        return buildNotification(
            R.drawable.accessibility_24,
            counter.toString(),
            Intent(this, TrackerService::class.java)
        )
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        val dataFromAccessibilityService = intent?.getStringExtra(DataKeyID)
        if (!dataFromAccessibilityService.isNullOrEmpty()) {
            showToastMsg(dataFromAccessibilityService)
            counter++
            updateNotification()
        }
        return START_STICKY
    }

}

