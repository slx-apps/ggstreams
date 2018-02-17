package com.nlx.ggstreams.keyboard

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.nlx.ggstreams.models.EmoteIcon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_emote_icon.view.*

class EmoteIconViewHolder(itemView: View, val picasso: Picasso) : RecyclerView.ViewHolder(itemView) {

    fun bind(icon: EmoteIcon) {
        picasso.load(icon.urls.big)
                .into(itemView.iv_emote_icon)
    }

}