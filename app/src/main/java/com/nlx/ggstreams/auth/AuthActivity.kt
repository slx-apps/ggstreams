package com.nlx.ggstreams.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.text.TextUtils
import com.nlx.ggstreams.R
import com.nlx.ggstreams.auth.login.LoginFragment
import com.nlx.ggstreams.auth.login.di.AuthManager
import dagger.android.AndroidInjection
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var manager: AuthManager

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AuthActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_auth)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        attachAuthFragment()
    }

    private fun attachAuthFragment() {
        val fragment: androidx.fragment.app.Fragment
        if (manager.profile.userId == -1 || TextUtils.isEmpty(manager.profile.token)) {
            fragment = LoginFragment.newInstance()
            title = getString(R.string.ft_title_login)
        } else {
            fragment = UserFragment.newInstance()
            title = getString(R.string.ft_title_user_account_info)
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container_auth, fragment)
        ft.commit()
    }
}
