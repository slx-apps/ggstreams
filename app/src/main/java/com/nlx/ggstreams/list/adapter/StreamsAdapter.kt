package com.nlx.ggstreams.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nlx.ggstreams.R
import com.nlx.ggstreams.models.GGStream
import com.squareup.picasso.Picasso

class StreamsAdapter (val context: Context,
                      val picasso: Picasso,
                      private val onClickListener: (GGStream) -> Unit) : RecyclerView.Adapter<StreamViewHolder>() {

    private var streamList: List<GGStream> = listOf()
    //private var listener: OnStreamClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_stream, parent, false)

        val viewHolder = StreamViewHolder(view, picasso)
        return viewHolder
    }

    override fun onBindViewHolder(holder: StreamViewHolder, position: Int) {
        val stream = streamList[position]

        holder.bind(stream)

        holder.itemView.setOnClickListener {
            //listener?.onStreamClicked(stream)
            onClickListener(stream)
        }
    }

    override fun getItemCount(): Int {
        return streamList.size
    }

//    fun setListener(listener: OnStreamClickListener?) {
//        this.listener = listener
//    }
//
//    interface OnStreamClickListener {
//        fun onStreamClicked(stream: GGStream)
//    }

    fun setList(list: List<GGStream>, refresh: Boolean) {
        this.streamList = list
        notifyDataSetChanged()
    }
}
