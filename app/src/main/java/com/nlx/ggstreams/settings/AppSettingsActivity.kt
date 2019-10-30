package com.nlx.ggstreams.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.nlx.ggstreams.R

class AppSettingsActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AppSettingsActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        val appSettingsFragment = AppSettingsFragment()
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.settings_container, appSettingsFragment)
        ft.commit()
    }
}