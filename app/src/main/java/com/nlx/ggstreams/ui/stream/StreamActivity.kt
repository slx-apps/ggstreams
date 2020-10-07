package com.nlx.ggstreams.ui.stream

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.nlx.ggstreams.R
import com.nlx.ggstreams.models.GGStream
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StreamActivity : RxAppCompatActivity() {

    companion object {
        const val TAG = "StreamActivity"
        const val KEY_STREAM = "stream"

        fun toIntent(context: Context, ggStream: GGStream): Intent {
            val intent = Intent(context, StreamActivity::class.java)
            intent.putExtra(KEY_STREAM, ggStream)

            return intent
        }
    }

    private var stream: GGStream? = null
    private var fragment: StreamFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.StreamTheme)
        setContentView(R.layout.activity_stream)

        if (intent != null) {
            stream = intent.getParcelableExtra<GGStream>(KEY_STREAM)
        }

        title = stream?.key

        stream?.let {
            fragment = StreamFragment.newInstance(it)
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_root, fragment!!)
            ft.commit()
        } ?: finish()
    }

    override fun onBackPressed() {
        fragment?.onBackPressed()
    }

    fun close() {
        super.onBackPressed()
    }
}
