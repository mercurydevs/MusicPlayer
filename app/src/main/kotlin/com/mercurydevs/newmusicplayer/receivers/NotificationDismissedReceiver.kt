package com.mercurydevs.newmusicplayer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mercurydevs.newmusicplayer.services.MusicService

class NotificationDismissedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Intent(context, MusicService::class.java).apply {
            context.stopService(this)
        }
    }
}
