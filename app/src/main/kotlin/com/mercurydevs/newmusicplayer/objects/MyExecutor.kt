package com.mercurydevs.newmusicplayer.objects

import java.util.concurrent.Executors

object MyExecutor {
    val myExecutor = Executors.newSingleThreadExecutor()
}
