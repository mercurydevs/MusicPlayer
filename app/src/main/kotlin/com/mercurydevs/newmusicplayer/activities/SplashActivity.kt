package com.mercurydevs.newmusicplayer.activities

import android.content.Intent
import com.mercurydevs.commons.activities.BaseSplashActivity

class SplashActivity : BaseSplashActivity() {
    override fun initActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
