package com.nlx.ggstreams.chat.mvp

import com.nlx.ggstreams.auth.login.di.AuthManager
import com.nlx.ggstreams.chat.GGChat
import com.nlx.ggstreams.data.EmoteIconsRepo
import com.nlx.ggstreams.models.GGMessage
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.stream.mvp.StreamMVP
import com.nlx.ggstreams.utils.rx.RxUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fr_stream.*
import javax.inject.Inject


class StreamChatPresenter @Inject constructor(private val chat: GGChat,
                                              private val view: StreamMVP.StreamView,
                                              private val repo: EmoteIconsRepo,
                                              private val rxUtils: RxUtils) : StreamChatMVP.Presenter {
    private lateinit var stream: GGStream

    override fun getStream(): String = stream.playerSrc

    override var compositeDisposal = CompositeDisposable()

    override fun init(stream: GGStream) {
        this.stream = stream

        compositeDisposal.add(
                chat.messageObservable()
                        .observeOn(rxUtils.observeScheduler)
                        .subscribe({
                            view.onNewMessage(it)
                        }, {
                            it.printStackTrace()
                        })
        )

        compositeDisposal.add(
                repo.iconsObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            view.emoteIconsLoaded(it)
                        }, {
                            it.printStackTrace()
                        })
        )
        chat.joinChannel(stream.streamId)
    }

    override fun postMessage(newMessage: String) {
        chat.postMessage(newMessage)
    }

    override fun loadIcons() {
        compositeDisposal.add(
                repo.iconsObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            view.emoteIconsLoaded(it)
                        }, {
                            it.printStackTrace()
                        }))

        repo.loadEmoteIcons(stream.streamId.toString())

    }


    override fun disconnect() {
        chat.disconnect()
    }
}