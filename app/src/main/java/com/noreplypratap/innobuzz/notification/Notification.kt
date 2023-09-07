package com.noreplypratap.innobuzz.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.noreplypratap.innobuzz.R
import com.noreplypratap.innobuzz.utils.Constants.NOTIFICATION_CHANNEL_ID

@SuppressLint("ObsoleteSdkInt")
fun Context.buildNotification(
    icon: Int,
    msg: String,
    intent: Intent
): Notification {
	val pendingIntent = PendingIntent.getActivity(
		this,
		0,
		intent,
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			PendingIntent.FLAG_IMMUTABLE
		} else 0
	)

	val builder = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
		val b = Notification.Builder(this)
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
			b.setPriority(Notification.PRIORITY_MIN)
		}
		b
	} else {
		createChannel()
		Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
	}.setOngoing(true)
		.setOnlyAlertOnce(true)
		.setSmallIcon(icon)
		.setContentTitle(getString(R.string.app_name))
		.setContentText("WhatsApp Launched : $msg times")
		.setContentIntent(pendingIntent)
	return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {

		builder.notification
	} else {
		builder.build()
	}
}

fun Context.createChannel() {
	val nm = getSystemService(
		Context.NOTIFICATION_SERVICE
	) as NotificationManager
	if (nm.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null) {
		val channel = NotificationChannel(
			NOTIFICATION_CHANNEL_ID,
			getString(R.string.app_name),
			NotificationManager.IMPORTANCE_LOW
		)
		channel.description = getString(R.string.app_name)
		channel.lockscreenVisibility = Notification.VISIBILITY_SECRET
		channel.setSound(null, null)
		channel.setShowBadge(false)
		channel.enableLights(false)
		channel.enableVibration(false)
		nm.createNotificationChannel(channel)
	}
}
