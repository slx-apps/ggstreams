package com.nlx.ggstreams.data

import android.content.Context
import android.content.SharedPreferences
import com.nlx.ggstreams.models.ChatProfile
import com.nlx.ggstreams.models.EmoteIcon
import java.util.HashSet

class PreferencesUtils(val context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
    private var settings: SharedPreferences = context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE)

    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val RECENT = "recent"
        const val USER_ID = "user_id"
        const val LOGIN = "attachAuthFragment"

        const val PREFS_FILE = "auth.sp"
        const val SETTINGS_FILE = "settings.sp"
    }

    fun saveProfile(profile: ChatProfile) {
        val editor = prefs.edit()

        editor.putString(ACCESS_TOKEN, profile.token)
        editor.putInt(USER_ID, profile.userId)
        editor.putString(LOGIN, profile.login)

        editor.apply()
    }

    fun loadProfile(): ChatProfile {
        val chatProfile = ChatProfile()
        chatProfile.token = prefs.getString(ACCESS_TOKEN, "")
        chatProfile.userId = prefs.getInt(USER_ID, -1)
        chatProfile.login = prefs.getString(LOGIN, "")

        return chatProfile
    }

    fun clearUser() {
        val editor = prefs.edit()

        editor.putString(ACCESS_TOKEN, "")
        editor.putInt(USER_ID, -1)
        editor.putString(LOGIN, "")

        editor.apply()
    }

    fun addToRecent(emoteIcon: EmoteIcon) {
        val recent = settings.getStringSet(RECENT, HashSet<String>())

        val editor = settings.edit()
        recent!!.add(emoteIcon.key)

        editor.putStringSet(RECENT, recent)

        editor.apply()
    }

    fun getRecent(): Set<String> {
        return settings.getStringSet(RECENT, HashSet<String>())
    }

    fun shouldAutoPlay(): Boolean {
        return settings.getBoolean("stream.autoplay", true)
    }

    fun isScrollToLast(): Boolean {
        return settings.getBoolean("chat.scroll", true)
    }

    fun isKeepScreenOn(): Boolean {
        return settings.getBoolean("chat.awake", true)
    }

    fun isShowIcons(): Boolean {
        return settings.getBoolean("chat.icons", true)
    }

}
