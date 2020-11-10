package com.nlx.ggstreams.keyboard

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.nlx.ggstreams.databinding.ItemEmoteIconBinding
import com.nlx.ggstreams.models.EmoteIcon
import com.squareup.picasso.Picasso

class EmoteIconViewHolder(val view: ItemEmoteIconBinding,
                          val picasso: Picasso) : RecyclerView.ViewHolder(view.root) {

    fun bind(icon: EmoteIcon) {
        picasso.load(icon.urls.big)
                .into(view.ivEmoteIcon)
    }

}