package com.nlx.ggstreams.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.nlx.ggstreams.R

class AppSettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.app_preferences)
    }
}
