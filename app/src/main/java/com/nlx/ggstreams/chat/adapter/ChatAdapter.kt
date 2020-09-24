package com.nlx.ggstreams.chat.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.text.Spannable
import android.text.Spanned
import android.text.style.ImageSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.nlx.ggstreams.R
import com.nlx.ggstreams.data.EmoteIconsRepo
import com.nlx.ggstreams.models.GGMessage
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.row_chat_message.view.*
import java.lang.Exception


private val spannableFactory = Spannable.Factory.getInstance()

class ChatAdapter(val context: Context,
                  private val icons: EmoteIconsRepo,
                  private val listener: (GGMessage) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<MessageViewHolder>() {

    companion object {
        const val TAG = "ChatAdapter"
    }

    private val targets = ArrayList<EmoteIconTarget>()
    private var messages: MutableList<GGMessage> = mutableListOf()

    override fun getItemCount(): Int =  messages.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_chat_message, parent, false)

        val viewHolder = MessageViewHolder(view)
        return viewHolder
    }


    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val m = messages[position]

        holder.bind(m)

        val spannable:Spannable = spannableFactory.newSpannable(m.text)
        holder.setMessage(spannable)

        addEmoteIcons(spannable, holder.itemView.tvText)

        holder.itemView.setOnClickListener {
            listener(m)
        }

        holder.itemView.setOnLongClickListener {
            true
        }
    }

    private fun addEmoteIcons(spannable: Spannable, textView: TextView) {
        val matcher = icons.pattern.matcher(spannable)

        while (matcher.find()) {

            val emoteIcon = icons.getIcon(matcher.group(1))
            Log.d(TAG, "addEmoteIcons: " + emoteIcon)
            if (emoteIcon != null) {

//                val start: Int  = textView.selectionStart - 1
//                val end: Int = textView.selectionEnd


                val start = matcher.start()
                val end = matcher.end()

                val target = EmoteIconTarget(start, end, textView, spannable)
                targets.add(target)

                Picasso.get()
                        .load(emoteIcon.urls.big)
                        .placeholder(R.drawable.ic_insert_emoticon)
                        .resize(50, 50)
                        .into(target)

            }
        }
    }

    inner class EmoteIconTarget(private var start: Int,
                                private var end: Int,
                                private var textView: TextView,
                                private var spannable: Spannable) : Target {

        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
			val textView: TextView = textView

			// Get existing ImageSpans to add them later again,
			val spans: Array<ImageSpan> = spannable.getSpans(0, textView.length(), ImageSpan::class.java)

			// Create drawable from target bitmap
            val bitmapDrawable: BitmapDrawable = BitmapDrawable(textView.context.resources, bitmap)
            bitmapDrawable.setBounds(
                    0,
                    0,
                    bitmapDrawable.intrinsicWidth + 5,
                    bitmapDrawable.intrinsicHeight + 5)
            val imageSpan = ImageSpan(bitmapDrawable, ImageSpan.ALIGN_BOTTOM)

			// Add ImageSpan to the Spannable
			spannable.setSpan(imageSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

			// Add previously added ImageSpans
			if (spans.isNotEmpty()) {
                for (span in spans) {
                    spannable.setSpan(span,
                            spannable.getSpanStart(span),
                            spannable.getSpanEnd(span),
                            Spanned.SPAN_INCLUSIVE_INCLUSIVE)
                }
			}

			textView.setText(spannable, TextView.BufferType.SPANNABLE)

			targets.remove(this)
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            targets.remove(this)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable) {
            //
        }

    }

    fun addMessage(message: GGMessage) {
        val size = messages.size
        messages.add(message)
        notifyItemInserted(size)
    }
}