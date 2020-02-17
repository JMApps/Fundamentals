package jmapps.fundamentals

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import jmapps.fundamentals.ui.activities.MainActivity

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toMainActivity = Intent(this, MainActivity::class.java)
        startActivity(toMainActivity)
        finish()
    }
}