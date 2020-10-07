package com.nlx.ggstreams.ui.stream

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nlx.ggstreams.ui.auth.AuthManager
import com.nlx.ggstreams.chat.GGChat
import com.nlx.ggstreams.data.EmoteIconsRepo
import com.nlx.ggstreams.data.PreferencesUtils
import com.nlx.ggstreams.models.EmoteIcon
import com.nlx.ggstreams.models.GGMessage
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.utils.rx.RxUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class StreamViewModel @ViewModelInject constructor(private val preferencesUtils: PreferencesUtils,
                                                   private val model: AuthManager,
                                                   private val repo: EmoteIconsRepo,
                                                   private val chat: GGChat,
                                                   private val rxUtils: RxUtils) : ViewModel() {
    private var stream: GGStream? = null

    fun getStream(): String = stream?.playerSrc ?: ""

    var compositeDisposal = CompositeDisposable()

    private val _iconsLiveData = MutableLiveData<Map<String, EmoteIcon>>()
    private val _messagesLiveData = MutableLiveData<GGMessage>()

    fun init(stream: GGStream?) {
        this.stream = stream

        stream?.let {
            compositeDisposal.add(
                    chat.messageObservable()
                            .observeOn(rxUtils.observeScheduler)
                            .subscribe({
                                _messagesLiveData.postValue(it)
                            }, {
                                it.printStackTrace()
                            })
            )

            compositeDisposal.add(
                    repo.iconsObservable()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ map ->
                                _iconsLiveData.postValue(map)
                            }, {
                                it.printStackTrace()
                            })
            )
            chat.joinChannel(stream.streamId)

            compositeDisposal.add(
                    repo.iconsObservable()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ map ->
                                _iconsLiveData.postValue(map)
                            }, {
                                it.printStackTrace()
                            }))

            repo.loadEmoteIcons(it.streamId.toString())
        }
    }

    fun postMessage(newMessage: String) {
        chat.postMessage(newMessage)
    }


    private fun disconnect() {
        chat.disconnect()
    }

    fun shouldAutoPlay(): Boolean = preferencesUtils.shouldAutoPlay

    fun isScrollToLast(): Boolean = preferencesUtils.isScrollToLast

    override fun onCleared() {
        super.onCleared()
        compositeDisposal.dispose()
        disconnect()
    }
}