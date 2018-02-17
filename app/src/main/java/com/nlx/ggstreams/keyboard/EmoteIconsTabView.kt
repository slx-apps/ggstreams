package com.nlx.ggstreams.keyboard

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.nlx.ggstreams.AutofitRecyclerView
import com.nlx.ggstreams.R
import com.nlx.ggstreams.keyboard.EmoteIconsAdapter
import com.nlx.ggstreams.models.EmoteIcon
import com.squareup.picasso.Picasso


class EmoteIconsTabView(val context: Context,
                        val emoteIcons: List<EmoteIcon>,
                        val keyboard: EmoteIconsKeyboard,
                        val picasso: Picasso) {

    val rootView: View

    init {
        val inflater = context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        rootView = inflater.inflate(R.layout.layout_icons_tab, null)

        val gridView = rootView.findViewById<AutofitRecyclerView>(R.id.rv_icons_tab)
        val adapter = EmoteIconsAdapter(context, emoteIcons, picasso)
        adapter.listener = keyboard.onEmoteIconClickListener
        gridView.adapter = adapter
    }
}