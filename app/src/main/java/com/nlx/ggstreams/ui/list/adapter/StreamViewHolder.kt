package com.nlx.ggstreams.ui.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.nlx.ggstreams.databinding.RowStreamBinding
import com.nlx.ggstreams.models.GGStream
import com.squareup.picasso.Picasso


class StreamViewHolder(val view: RowStreamBinding,
                       val picasso: Picasso) : RecyclerView.ViewHolder(view.view) {

    fun bind(stream: GGStream) {

        // set Image
        picasso.load("http:" + stream.thumb)
                .fit()
                .into(view.ivPreview)


        // title
        view.tvTitle.text = stream.title

        // description
        view.tvStreamer.text = stream.key

        // viewers
        view.tvViewers.text = stream.viewers.toString()
    }
}
