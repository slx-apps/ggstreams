package com.nlx.ggstreams.utils.extensions

import android.content.Context
import android.widget.Toast

fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toast(message: Int) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()