package com.nlx.ggstreams.chat

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.nlx.ggstreams.ui.auth.AuthManager
import com.nlx.ggstreams.models.*
import com.nlx.ggstreams.rest.GGApi
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import okhttp3.*
import okhttp3.Response

class GGChat(private val client: OkHttpClient,
             val api: GGApi,
             private val gson: Gson,
             private val manager: AuthManager
    ) : WebSocketListener() {

    private val parser: JsonParser = JsonParser()
    private var channelId = -1

    private val chatMessagePublishSubject : PublishSubject<GGMessage> = PublishSubject.create<GGMessage>()
    private val viewersSubject : PublishSubject<Int> = PublishSubject.create<Int>()
    private val subscriptions = CompositeDisposable()

    var webSocket: WebSocket? = null


    fun joinChannel(channel: Int) {
        Log.d(TAG, "joinChannel: $channel")

        channelId = channel
        if (channelId >= 0) {
            connect()
        }

        manager.profileObservable()
               .subscribe { chatProfile -> login(chatProfile) }

        login(manager.profile)
    }

    override fun onOpen(webSocket: WebSocket?, response: Response?) {
        super.onOpen(webSocket, response)
        Log.d(TAG, "onOpen")

        val dataContainer = DataContainer(CHAT_TYPE_JOIN, PostMessage(channelId, null, CHAT_TYPE_JOIN))

        webSocket?.send(gson.toJson(dataContainer))
    }

    override fun onMessage(webSocket: WebSocket?, text: String?) {
        super.onMessage(webSocket, text)
        Log.d(TAG, "received message: $text")

        val messageObject = parser.parse(text).asJsonObject

        if (messageObject.has(TYPE_MESSAGE)) {
            val messageType = messageObject.get(TYPE_MESSAGE).asString

            val data = messageObject.get(DATA_CONTAINER)

            when (messageType) {
                CHAT_TYPE_WELCOME -> {
                    //
                }

                CHAT_TYPE_CHANNEL_COUNTERS -> {
                    val countersData = gson.fromJson(data, CountersData::class.java)
                    viewersSubject.onNext(countersData.clientsInChannel)
                }

                CHAT_TYPE_MESSAGE -> {
                    val ggMessage = gson.fromJson(data, GGMessage::class.java)
                    chatMessagePublishSubject.onNext(ggMessage)
                }

                CHAT_TYPE_SUCCESS_JOIN -> {
                    //
                }

                CHAT_TYPE_SUCCESS_AUTH -> {

                }

                else -> Log.e(TAG, "No such message type $messageType")
            }
        }
    }

    override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
        super.onClosing(webSocket, code, reason)

        webSocket?.close(1000, null)
        Log.d(TAG, "closed with exit code $code additional info: $reason")
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
        super.onFailure(webSocket, t, response)
        t?.printStackTrace()
    }

    private fun connect() {
        if (channelId > -1) {
            Log.d(TAG, "connect")

            val request = Request.Builder()
                    .url(CHAT_WS_URL)
                    .build()

            webSocket = client.newWebSocket(request, this)
        } else {
            Log.e(TAG, "connect: channelId does not exists")
        }
    }

    fun disconnect() {
        webSocket?.close(1000, "Closing")
    }

    private fun login(chatProfile: ChatProfile) {
        if (chatProfile.userId == -1 || TextUtils.isEmpty(chatProfile.token)) {
            Log.e(TAG, "attachAuthFragment: profile is empty")
            return
        }

        Log.d(TAG, "login: $chatProfile")

        val dataContainer = DataContainer(CHAT_TYPE_AUTH, AuthRequest(chatProfile.userId, chatProfile.token))

        webSocket?.send(gson.toJson(dataContainer))
    }

    fun postMessage(message: String) {
        if (!TextUtils.isEmpty(message)) {
            val postMessage = PostMessage(channelId, message, CHAT_TYPE_SEND_MESSAGE)
            val dataContainer = DataContainer(CHAT_TYPE_SEND_MESSAGE, postMessage)
            webSocket?.send(gson.toJson(dataContainer))
        }
    }

    fun messageObservable() : Flowable<GGMessage> {
        return chatMessagePublishSubject.toFlowable(BackpressureStrategy.BUFFER)
    }

    companion object {
        const val TAG = "GGChat"

        const val CHAT_WS_URL = "ws://chat.goodgame.ru:8081/chat/websocket"
        const val CHAT_TYPE_WELCOME = "welcome"

        const val CHAT_TYPE_GET_CHANNEL_HISTORY = "get_channel_history"
        const val CHAT_TYPE_CHANNEL_HISTORY = "channel_history"

        const val CHAT_TYPE_JOIN = "join"
        const val CHAT_TYPE_SUCCESS_JOIN = "success_join"
        const val CHAT_TYPE_SUCCESS_AUTH = "success_auth"

        const val CHAT_TYPE_AUTH = "auth"

        const val CHAT_TYPE_SUCCESS_UNJOIN = "success_unjoin"
        const val CHAT_TYPE_UNJOIN = "unjoin"

        const val CHAT_TYPE_CHANNEL_COUNTERS = "channel_counters"
        const val CHAT_TYPE_MESSAGE = "message"

        const val CHAT_TYPE_SEND_MESSAGE = "send_message"


        const val TYPE_MESSAGE = "type"
        const val DATA_CONTAINER = "data"
    }

}