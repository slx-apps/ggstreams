package com.nlx.ggstreams.list.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.nlx.ggstreams.models.GGStream
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_stream.view.*


class StreamViewHolder(view: View, val picasso: Picasso) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    fun bind(stream: GGStream) {

        // set Image
        picasso.load("http:" + stream.thumb)
                .fit()
                .into(itemView.ivPreview)


        // title
        itemView.tvTitle.text = stream.title

        // description
        itemView.tvStreamer.text = stream.key

        // viewers
        itemView.tvViewers.text = stream.viewers.toString()
    }
}
