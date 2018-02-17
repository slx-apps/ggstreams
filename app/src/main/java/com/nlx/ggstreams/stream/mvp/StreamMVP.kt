package com.nlx.ggstreams.stream.mvp

import com.nlx.ggstreams.chat.mvp.StreamChatMVP
import com.nlx.ggstreams.mvp.BasePresenter
import com.nlx.ggstreams.mvp.BaseView


interface StreamMVP {

    interface Model {

    }

    interface Presenter: BasePresenter {
        fun shouldAutoPlay(): Boolean
        fun isScrollToLast(): Boolean


    }

    interface View: BaseView {

    }

    interface StreamView: StreamMVP.View, StreamChatMVP.View {

    }
}