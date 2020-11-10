package com.nlx.ggstreams.keyboard

import com.nlx.ggstreams.models.EmoteIcon

public interface OnEmoteIconClickListener {
    fun onEmoteIconClick(emoteIcon: EmoteIcon)
    fun onEmoteIconLongClick(emoteIcon: EmoteIcon)
}

