package com.mercurydevs.newmusicplayer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mercurydevs.newmusicplayer.extensions.sendIntent
import com.mercurydevs.newmusicplayer.helpers.FINISH
import com.mercurydevs.newmusicplayer.helpers.NEXT
import com.mercurydevs.newmusicplayer.helpers.PLAYPAUSE
import com.mercurydevs.newmusicplayer.helpers.PREVIOUS

class ControlActionsListener : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        when (action) {
            PREVIOUS, PLAYPAUSE, NEXT, FINISH -> context.sendIntent(action)
        }
    }
}
