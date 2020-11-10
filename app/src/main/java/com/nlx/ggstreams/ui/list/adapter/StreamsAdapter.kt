package com.nlx.ggstreams.ui.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.nlx.ggstreams.R
import com.nlx.ggstreams.databinding.RowStreamBinding
import com.nlx.ggstreams.models.GGStream
import com.squareup.picasso.Picasso

class StreamsAdapter (val context: Context,
                      val picasso: Picasso,
                      private val onClickListener: (GGStream) -> Unit
                      ) : PagingDataAdapter<GGStream, StreamViewHolder>(STREAMS_DIFF_CALLBACK) {

    companion object {

        val STREAMS_DIFF_CALLBACK = object : DiffUtil.ItemCallback<GGStream>() {

            override fun areItemsTheSame(oldStream: GGStream,
                                         newStream: GGStream): Boolean =
                    oldStream.key == newStream.key

            override fun areContentsTheSame(oldStream: GGStream,
                                            newStream: GGStream): Boolean =
                    oldStream == newStream
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamViewHolder {
        val view = RowStreamBinding.inflate(LayoutInflater.from(context), parent, false)

        val viewHolder = StreamViewHolder(view, picasso)
        return viewHolder
    }

    override fun onBindViewHolder(holder: StreamViewHolder, position: Int) {
        val stream = getItem(position)

        stream?.let {
            holder.bind(it)

            holder.itemView.setOnClickListener {
                onClickListener(stream)
            }
        }

    }
}
