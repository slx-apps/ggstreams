package com.nlx.ggstreams.chat.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.nlx.ggstreams.R
import com.nlx.ggstreams.databinding.RowChatMessageBinding
import com.nlx.ggstreams.models.GGMessage

class MessageViewHolder(val view: RowChatMessageBinding) : RecyclerView.ViewHolder(view.root) {

    private lateinit var message: GGMessage

    fun bind(message: GGMessage) {
        this.message = message

        setMessage(message.text)
        setFrom(message.userName, message.color)
    }

    fun setMessage(message: CharSequence?) {
        if (message == null) return
        view.tvText.text = message
    }

    private fun setFrom(user: String, color: String) {
        when (color) {
            "simple" -> view.tvFrom.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.silver))
            "silver" -> view.tvFrom.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.silver))
            "gold" -> view.tvFrom.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.gold))
            "premium-personal" -> view.tvFrom.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.premium))
        }

        if (user.isEmpty()) {
            view.tvFrom.text = ""
        } else {
            view.tvFrom.text = user
        }
    }
}
