package com.mercurydevs.newmusicplayer

import android.app.Application
import com.mercurydevs.commons.extensions.checkUseEnglish

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        checkUseEnglish()
    }
}
