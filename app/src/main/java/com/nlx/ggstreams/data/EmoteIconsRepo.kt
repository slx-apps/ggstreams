package com.nlx.ggstreams.data

import android.util.Log
import com.jakewharton.rxrelay2.BehaviorRelay
import com.nlx.ggstreams.models.EmoteIcon
import com.nlx.ggstreams.models.EmoteIconsResponse
import com.nlx.ggstreams.rest.GGApi
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.regex.Pattern

class EmoteIconsRepo(val api: GGApi, val utils: PreferencesUtils) {

    private var emoteIconsLoaded = false

    /**
     * :key:, EmoteIcon (key w/o :)
     */
    private var emoteIcons: MutableMap<String, EmoteIcon> = HashMap()
    private var emoteIconList: MutableList<EmoteIcon> = ArrayList()
    private val channelEmoteIcons = ArrayList<EmoteIcon>()

    private val iconsRelay = BehaviorRelay.create<Map<String, EmoteIcon>>()
    private var channelId: String = ""
    val pattern: Pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE)

    fun getIcon(key: String): EmoteIcon? = emoteIcons[key]

    companion object {
        const val TAG = "EmoteIconsRepo"
        const val REGEX = "(:[a-z0-9-_]+:)"

        const val PATTERN_COLUMN: String = ":"
    }

    fun loadEmoteIcons(channelId: String) {
        this.channelId = channelId// TODO remove state
        Log.d(TAG, "loadEmoteIcons")

        if (!emoteIconsLoaded) {
            getPagedEmoteIcons(1)
                    .subscribeOn(Schedulers.io())
                    .concatMap { response -> Observable.fromIterable(response.smileyContainer?.emoteIcons) }
                    .subscribeWith(object : Observer<Any> {
                        override fun onSubscribe(@NonNull d: Disposable) {
                            emoteIconList = LinkedList<EmoteIcon>()
                        }

                        override fun onNext(@NonNull smile: Any) {
                            emoteIconList.add(smile as EmoteIcon)
                        }

                        override fun onError(@NonNull e: Throwable) {
                            e.printStackTrace()
                        }

                        override fun onComplete() {
                            Log.d(TAG, "GG Emote icons load finished")
                            emoteIconsLoaded = true

                            if (emoteIcons == null) {
                                emoteIcons = HashMap<String, EmoteIcon>()
                            }
                            for (emoteIcon in emoteIconList) {
                                emoteIcons.put(PATTERN_COLUMN + emoteIcon.key.toLowerCase() + PATTERN_COLUMN, emoteIcon)
                            }
                            iconsRelay.accept(emoteIcons)
                        }
                    })
        }

        if (channelId.isNotEmpty()) {
            getPagedEmoteIcons(1, channelId)
                    .subscribeOn(Schedulers.io())
                    .concatMap { response -> Observable.fromIterable(response.smileyContainer?.emoteIcons) }
                    .subscribeWith(object : Observer<Any> {
                        override fun onSubscribe(@NonNull d: Disposable) {
                            channelEmoteIcons.clear()
                        }

                        override fun onNext(@NonNull smile: Any) {
                            channelEmoteIcons.add(smile as EmoteIcon)
                        }

                        override fun onError(@NonNull e: Throwable) {

                        }

                        override fun onComplete() {
                            Log.d(TAG, "GG Emote icons load finished")
                            emoteIconsLoaded = true

                            if (emoteIcons == null) {
                                emoteIcons = HashMap<String, EmoteIcon>()
                            }

                            //emoteIcons.clear();

                            for (smile in channelEmoteIcons) {
                                emoteIcons.put(":" + smile.key?.toLowerCase() + ":", smile)
                            }

                        }
                    })
        }
    }

    private fun getPagedEmoteIcons(page: Int, channelId: String): Observable<EmoteIconsResponse> {
        return api.channelSmilesObservable(channelId, page)
                .concatMap(Function<EmoteIconsResponse, ObservableSource<out EmoteIconsResponse>> { response ->
                    // Terminal case.
                    if (response.page == response.pageCount) {
                        return@Function Observable.just(response)
                    }
                    Observable.just(response).concatWith(getPagedEmoteIcons(response.page + 1, channelId))
                })
    }


    private fun getPagedEmoteIcons(page: Int): Observable<EmoteIconsResponse> {
        return api.allSmilesObservable(page)
                .concatMap(Function<EmoteIconsResponse, ObservableSource<out EmoteIconsResponse>> { response ->
                    // Terminal case.
                    if (response.page == response.pageCount) {
                        return@Function Observable.just(response)
                    }
                    Observable.just(response).concatWith(getPagedEmoteIcons(response.page + 1))
                })
    }

    fun isEmoteIconsLoaded(): Boolean {
        return emoteIconsLoaded
    }

    fun iconsObservable(): Flowable<Map<String, EmoteIcon>> {
        return iconsRelay.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getEmoteIcons(): Map<String, EmoteIcon> {
        return emoteIcons
    }

    fun getEmoteIconsList(): List<EmoteIcon> {
        val freeIcons = ArrayList<EmoteIcon>()

        for (icon in emoteIconList) {
            if (!icon.isPremium && !icon.isPaid) {
                if (icon.channelId == "0" || icon.channelId == channelId) {
                    freeIcons.add(icon)
                }
            }
        }
        return freeIcons
    }

    fun addToRecent(emoteIcon: EmoteIcon) {
        utils.addToRecent(emoteIcon)
    }


    fun getRecent(): List<EmoteIcon> {
        val freeIcons = ArrayList<EmoteIcon>()
        val recent = utils.getRecent()
        for (icon in emoteIconList) {
            if (!icon.isPremium && !icon.isPaid) {
                if ((icon.channelId == "0" || icon.channelId == channelId) && recent.contains(icon.key)) {
                    freeIcons.add(icon)
                }
            }
        }
        return freeIcons
    }
}
