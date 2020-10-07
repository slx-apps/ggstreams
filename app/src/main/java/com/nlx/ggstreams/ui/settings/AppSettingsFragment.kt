package com.nlx.ggstreams.ui.settings

import android.os.Bundle
import android.preference.PreferenceFragment
import com.nlx.ggstreams.R

class AppSettingsFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.app_preferences)
    }
}
