package com.nlx.ggstreams.stream

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.nlx.ggstreams.R
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.stream.StreamFragment
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import java.util.stream.Stream

val KEY_STREAM = "stream"

class StreamActivity : RxAppCompatActivity() {

    companion object {
        val TAG = "StreamActivity"
    }

    private lateinit var stream: GGStream
    private var fragment: StreamFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.StreamTheme)
        setContentView(R.layout.activity_stream)

        if (intent != null) {
            stream = intent.getParcelableExtra<GGStream>(KEY_STREAM)
        }

        
        title = stream.key


        fragment = StreamFragment.newInstance(stream)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.content_root, fragment)
        ft.commit()
    }

    override fun onBackPressed() {
        fragment?.onBackPressed()
    }

    fun close() {
        super.onBackPressed()
    }

    fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}
