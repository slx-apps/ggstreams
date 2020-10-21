package com.nlx.ggstreams.ui

import android.os.Bundle
import com.nlx.ggstreams.R
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : RxAppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.TransparentTheme)
        setContentView(R.layout.activity_main)
    }
}
