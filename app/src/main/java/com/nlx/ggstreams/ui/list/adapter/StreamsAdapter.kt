package com.nlx.ggstreams.ui.list.adapter

import androidx.paging.PagedListAdapter
import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nlx.ggstreams.R
import com.nlx.ggstreams.models.GGStream
import com.squareup.picasso.Picasso

class StreamsAdapter (val context: Context,
                      val picasso: Picasso,
                      private val onClickListener: (GGStream) -> Unit
                      ) : PagedListAdapter<GGStream, StreamViewHolder>(STREAMS_DIFF_CALLBACK) {

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
        val view = LayoutInflater.from(context).inflate(R.layout.row_stream, parent, false)

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
