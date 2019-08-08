package com.nlx.ggstreams.data

import android.content.Context
import android.preference.PreferenceManager
import com.nlx.ggstreams.models.ChatProfile
import com.nlx.ggstreams.models.EmoteIcon
import java.util.HashSet
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferencesUtils(val context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val RECENT = "recent"
        const val USER_ID = "user_id"
        const val LOGIN = "attachAuthFragment"
    }

    fun saveProfile(profile: ChatProfile) {
        val editor = preferences.edit()

        editor.putString(ACCESS_TOKEN, profile.token)
        editor.putInt(USER_ID, profile.userId)
        editor.putString(LOGIN, profile.login)

        editor.apply()
    }

    fun loadProfile(): ChatProfile {
        val chatProfile = ChatProfile()
        chatProfile.token = preferences.getString(ACCESS_TOKEN, "")
        chatProfile.userId = preferences.getInt(USER_ID, -1)
        chatProfile.login = preferences.getString(LOGIN, "")

        return chatProfile
    }

    fun clearUser() {
        val editor = preferences.edit()

        editor.putString(ACCESS_TOKEN, "")
        editor.putInt(USER_ID, -1)
        editor.putString(LOGIN, "")

        editor.apply()
    }

    fun addToRecent(emoteIcon: EmoteIcon) {
        val recent = preferences.getStringSet(RECENT, HashSet<String>())

        val editor = preferences.edit()
        recent!!.add(emoteIcon.key)

        editor.putStringSet(RECENT, recent)

        editor.apply()
    }

    fun getRecent(): Set<String> {
        return preferences.getStringSet(RECENT, HashSet<String>())
    }

    var shouldAutoPlay by PreferenceFieldDelegate.Boolean("stream.autoplay")
    var isScrollToLast by PreferenceFieldDelegate.Boolean("chat.scroll")
    var isKeepScreenOn by PreferenceFieldDelegate.Boolean("chat.awake")
    var isShowIcons by PreferenceFieldDelegate.Boolean("chat.icons")


    sealed class PreferenceFieldDelegate<T>(protected val key: kotlin.String)
        : ReadWriteProperty<PreferencesUtils, T> {

        class Boolean(key: kotlin.String) : PreferenceFieldDelegate<kotlin.Boolean>(key) {

            override fun getValue(thisRef: PreferencesUtils, property: KProperty<*>)
                    = thisRef.preferences.getBoolean(key, false)

            override fun setValue(thisRef: PreferencesUtils, property: KProperty<*>, value: kotlin.Boolean)
                    = thisRef.preferences.edit().putBoolean(key, value).apply()
        }

        class Int(key: kotlin.String) : PreferenceFieldDelegate<kotlin.Int>(key) {

            override fun getValue(thisRef: PreferencesUtils, property: KProperty<*>)
                    = thisRef.preferences.getInt(key, 0)

            override fun setValue(thisRef: PreferencesUtils, property: KProperty<*>, value: kotlin.Int)
                    = thisRef.preferences.edit().putInt(key, value).apply()
        }

        class Long(key: kotlin.String) : PreferenceFieldDelegate<kotlin.Long>(key) {

            override fun getValue(thisRef: PreferencesUtils, property: KProperty<*>)
                    = thisRef.preferences.getLong(key, -1)

            override fun setValue(thisRef: PreferencesUtils, property: KProperty<*>, value: kotlin.Long)
                    = thisRef.preferences.edit().putLong(key, value).apply()
        }

        class String(key: kotlin.String) : PreferenceFieldDelegate<kotlin.String>(key) {

            override fun getValue(thisRef: PreferencesUtils, property: KProperty<*>)
                    = thisRef.preferences.getString(key, "")

            override fun setValue(thisRef: PreferencesUtils, property: KProperty<*>, value: kotlin.String)
                    = thisRef.preferences.edit().putString(key, value).apply()
        }
    }
}
