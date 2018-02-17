package com.nlx.ggstreams.chat.mvp

import com.nlx.ggstreams.models.EmoteIcon
import com.nlx.ggstreams.models.GGMessage
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.mvp.BasePresenter
import com.nlx.ggstreams.mvp.BaseView


interface StreamChatMVP {

    interface Presenter: BasePresenter {
        fun loadIcons()
        fun init(stream: GGStream)
        fun postMessage(newMessage: String)
        fun getStream(): String
        fun disconnect()
    }

    interface View: BaseView {
        fun emoteIconsLoaded(icons: Map<String, EmoteIcon>)
        fun onNewMessage(message: GGMessage)
    }

}
