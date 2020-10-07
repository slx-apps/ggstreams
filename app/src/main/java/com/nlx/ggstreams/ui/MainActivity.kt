package com.nlx.ggstreams.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.nlx.ggstreams.R
import com.nlx.ggstreams.ui.auth.AuthActivity
import com.nlx.ggstreams.ui.auth.AuthManager
import com.nlx.ggstreams.ui.list.StreamListFragment
import com.nlx.ggstreams.ui.settings.AppSettingsActivity
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : RxAppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    @Inject
    lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setSupportActionBar(toolbar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        attachStreamList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val logoutItem = menu.findItem(R.id.action_log_out)
        val loginItem = menu.findItem(R.id.action_login)

        val userLoggedId = authManager.profile.token.isNotEmpty()
        logoutItem.isVisible = userLoggedId
        loginItem.isVisible = !userLoggedId


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            AppSettingsActivity.start(this)
            return true
        } else if (id == R.id.action_login) {
            AuthActivity.start(this)
        } else if (id == R.id.action_log_out) {
            authManager.logout()
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onStart() {
        super.onStart()

        authManager.profileObservable()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    invalidateOptionsMenu()
                }, {
                    Log.e(TAG, it.message ?: getString(R.string.error_general))
                })
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.navigation_stream_list -> {

                attachStreamList()

                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_favorites ->

                return@OnNavigationItemSelectedListener true
        }
        false
    }

    private fun attachStreamList() {
        val fragment = StreamListFragment.newInstance()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.content_root, fragment)
        ft.commit()
    }
}
