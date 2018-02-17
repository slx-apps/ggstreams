package com.nlx.ggstreams.chat.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.nlx.ggstreams.R
import com.nlx.ggstreams.models.GGMessage
import kotlinx.android.synthetic.main.row_chat_message.view.*

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var message: GGMessage

    fun bind(message: GGMessage) {
        this.message = message

        setMessage(message.text)
        setFrom(message.userName, message.color)
    }

    fun setMessage(message: CharSequence?) {
        if (message == null) return
        itemView.tvText.text = message
    }

    private fun setFrom(user: String, color: String) {
        when (color) {
            "simple" -> itemView.tvFrom.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.silver))
            "silver" -> itemView.tvFrom.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.silver))
            "gold" -> itemView.tvFrom.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.gold))
            "premium-personal" -> itemView.tvFrom.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.premium))
        }

        if (user.isEmpty()) {
            itemView.tvFrom.text = ""
        } else {
            itemView.tvFrom.text = user
        }
    }
}
