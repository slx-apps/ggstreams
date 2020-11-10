package com.nlx.ggstreams.keyboard

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nlx.ggstreams.R
import com.nlx.ggstreams.databinding.ItemEmoteIconBinding
import com.nlx.ggstreams.models.EmoteIcon
import com.squareup.picasso.Picasso

class EmoteIconsAdapter(val context: Context,
                        val list: List<EmoteIcon>,
                        val picasso: Picasso) : RecyclerView.Adapter<EmoteIconViewHolder>() {

    var listener: OnEmoteIconClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmoteIconViewHolder {
        val view = ItemEmoteIconBinding.inflate(LayoutInflater.from(context), parent, false)

        val viewHolder = EmoteIconViewHolder(view, picasso)
        return viewHolder
    }

    override fun onBindViewHolder(holder: EmoteIconViewHolder, position: Int) {
        val emoteIcon = list[position]

        holder.view.ivEmoteIcon.setOnClickListener {
            listener?.onEmoteIconClick(emoteIcon)
        }

        holder.view.ivEmoteIcon.setOnLongClickListener(View.OnLongClickListener {
            listener?.onEmoteIconLongClick(emoteIcon)
            return@OnLongClickListener true
        })
        holder.bind(emoteIcon)

    }

    override fun getItemCount(): Int {
        return list.size
    }

}