package com.nlx.ggstreams.stream

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Spanned
import android.text.style.ImageSpan
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.*
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.jakewharton.rxbinding2.view.RxView
import com.nlx.ggstreams.R
import com.nlx.ggstreams.auth.AuthManager
import com.nlx.ggstreams.chat.adapter.ChatAdapter
import com.nlx.ggstreams.chat.mvp.StreamChatMVP
import com.nlx.ggstreams.data.EmoteIconsRepo
import com.nlx.ggstreams.keyboard.EmoteIconsKeyboard
import com.nlx.ggstreams.keyboard.OnEmoteIconClickListener
import com.nlx.ggstreams.models.EmoteIcon
import com.nlx.ggstreams.models.GGMessage
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.rest.GGRestClient.Companion.GOODGAME_API_HLS
import com.nlx.ggstreams.stream.mvp.StreamMVP
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.trello.rxlifecycle2.components.support.RxFragment
import kotlinx.android.synthetic.main.fr_stream.*
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.*
import javax.inject.Inject

class StreamFragment : RxFragment(), Player.EventListener, PlaybackControlView.VisibilityListener,
        OnEmoteIconClickListener, EmoteIconsKeyboard.OnIconRemoveClickListener,
        StreamMVP.StreamView {

    @Inject
    lateinit var repo: EmoteIconsRepo
    @Inject
    lateinit var authManager: AuthManager
    @Inject
    lateinit var picasso: Picasso
    @Inject
    lateinit var bandwidthMeter: DefaultBandwidthMeter
    @Inject
    lateinit var cookieManager: CookieManager

    @Inject
    lateinit var streamPresenter: StreamMVP.Presenter
    @Inject
    lateinit var chatPresenter: StreamChatMVP.Presenter

    lateinit var stream : GGStream

    // Player
    private var mainHandler: Handler? = null
    private var window: Timeline.Window? = null
    //
    private var eventLogger: EventLogger? = null
    private var videoTrackSelectionFactory: TrackSelection.Factory? = null
    private var mediaDataSourceFactory: DataSource.Factory? = null
    //
    private var player: SimpleExoPlayer? = null
    private var trackSelector: DefaultTrackSelector? = null
    private var playerNeedsSource: Boolean = false

    private var shouldAutoPlay = true
    private var isTimelineStatic: Boolean = false
    private var playerWindow: Int = 0
    private var playerPosition: Long = 0
    //

    // Chat
    private var emoteIconsKeyboard: EmoteIconsKeyboard? = null
    private var isScrollToLast = true
    private var linearLayoutManager: androidx.recyclerview.widget.LinearLayoutManager? = null
    private var targets = ArrayList<EditTextTarget>()
    private var fullScreenFlags: Int = 0
    private var normalFlags: Int = 0

    private var isFullScreen = true

    private val adapter: ChatAdapter by lazy  {
        ChatAdapter(context!!, repo) {
            etMessage.text.append(it.userName + ", ")
        }
    }

    companion object {
        const val TAG = "StreamFragment"
        fun newInstance(stream : GGStream): StreamFragment {
            val newsFragment = StreamFragment()
            val args = Bundle()
            args.putParcelable(StreamActivity.KEY_STREAM, stream)
            newsFragment.arguments = args
            return newsFragment
        }
    }

    override fun onAttach(context: Context) {
        //AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER)

        val args = arguments
        if (args != null) {
            stream = args.getParcelable(StreamActivity.KEY_STREAM)
        }

        setHasOptionsMenu(true)


        shouldAutoPlay = streamPresenter.shouldAutoPlay()
        isScrollToLast = streamPresenter.isScrollToLast()

        fullScreenFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or // bottom buttons
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE//_STICKY

        normalFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fr_stream, container, false) as View
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as StreamActivity).setSupportActionBar(toolbar)



        // Set the padding to match the Status Bar height
        val statusBarHeight = getStatusBarHeight()
        toolbar?.setPadding(0, statusBarHeight, 0, 0)

        linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, true)
        rvChat.layoutManager = linearLayoutManager
        rvChat.adapter = adapter
        rvChat.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rvChat.setHasFixedSize(false)

        shouldAutoPlay = true
        mediaDataSourceFactory = DefaultDataSourceFactory(activity, "f")
        mainHandler = Handler()
        window = Timeline.Window()

        if (CookieHandler.getDefault() !== cookieManager) {
            CookieHandler.setDefault(cookieManager)
        }
        val height = playerView.height
        height + statusBarHeight

        playerView.setControllerVisibilityListener(this)
        playerView.requestFocus()

        chatPresenter.init(stream)
        chatPresenter.loadIcons()

        if (authManager.profile.token.isNotEmpty()) {
            containerNewMessage.visibility = View.VISIBLE
        }

        RxView.clicks(ivSend)
                .compose(bindToLifecycle<Any>())
                .doAfterNext { etMessage.text.clear() }
                .subscribe {
                    chatPresenter.postMessage(etMessage.text.toString())
                }

        RxView.clicks(ivOpenEmoteIcons)
                .compose(bindToLifecycle<Any>())
                .subscribe {
                    showKeyboard()
                }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fr_stream, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            showSettings()
        } else if (id == R.id.action_retry) {
            initializePlayer(chatPresenter.getStream())
        } else if (id == R.id.action_fullscreen) {
            openFullscreen(isFullScreen)
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer(chatPresenter.getStream())
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer(chatPresenter.getStream())
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }

        chatPresenter.disconnect()

        emoteIconsKeyboard?.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        targets.clear()
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {

    }

    override fun onTimelineChanged(timeline: Timeline, o: Any) {
        isTimelineStatic = !timeline.isEmpty && !timeline.getWindow(timeline.windowCount - 1, window).isDynamic
    }

    override fun onTracksChanged(trackGroupArray: TrackGroupArray, trackSelectionArray: TrackSelectionArray) {
        updateButtonVisibilities()
        val mappedTrackInfo = trackSelector?.currentMappedTrackInfo
        if (mappedTrackInfo != null) {
            if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_VIDEO) ==
                    MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                showToast(R.string.error_unsupported_video)
            }
            if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_AUDIO) ==
                    MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                showToast(R.string.error_unsupported_audio)
            }
        }
    }

    private fun showToast(res: Int) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
    }

    override fun onLoadingChanged(b: Boolean) {
        // Do nothing.
    }

    override fun onPlayerStateChanged(b: Boolean, playbackState: Int) {
        if (playbackState == ExoPlayer.STATE_ENDED) {
            //showControls()
        } else if (playbackState == ExoPlayer.STATE_BUFFERING) {
            progressBar.visibility = View.VISIBLE
        }
        updateButtonVisibilities()
    }

    override fun onPlayerError(e: ExoPlaybackException) {
        var errorString: String? = null
        if (e.type == ExoPlaybackException.TYPE_RENDERER) {
            val cause = e.rendererException
            if (cause is MediaCodecRenderer.DecoderInitializationException) {
                // Special case for decoder initialization failures.
                val decoderInitializationException = cause
                if (decoderInitializationException.decoderName == null) {
                    if (decoderInitializationException.cause is MediaCodecUtil.DecoderQueryException) {
                        errorString = getString(R.string.error_querying_decoders)
                    } else if (decoderInitializationException.secureDecoderRequired) {
                        errorString = getString(R.string.error_no_secure_decoder,
                                decoderInitializationException.mimeType)
                    } else {
                        errorString = getString(R.string.error_no_decoder,
                                decoderInitializationException.mimeType)
                    }
                } else {
                    errorString = getString(R.string.error_instantiating_decoder,
                            decoderInitializationException.decoderName)
                }
            }
        }
        if (errorString != null) {
            //showToast(errorString);
        }
        playerNeedsSource = true
        updateButtonVisibilities()
    }

    override fun onPositionDiscontinuity(reason: Int) {
        // Do nothing.
    }

    override fun onSeekProcessed() {
        // Do nothing.
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        // Do nothing.
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        // Do nothing.
    }

    override fun onVisibilityChange(visibility: Int) {
        if (visibility == View.GONE) {
            activity?.window?.decorView?.systemUiVisibility = fullScreenFlags
        } else if (visibility == View.VISIBLE) {
            activity?.window?.decorView?.systemUiVisibility = normalFlags
        }
    }


    private fun initializePlayer(channel: String?) {
        if (channel == null) {
            Log.e(TAG, "initializePlayer: channel is null")
            return
        }
        if (player == null) {
            videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)

            trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

            val rendererFactory = DefaultRenderersFactory(context, null, DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF)


            player = ExoPlayerFactory.newSimpleInstance(rendererFactory, trackSelector)
            player?.let {
                it.addListener(this)
                eventLogger = EventLogger(trackSelector)
                it.addListener(eventLogger)
                it.setAudioDebugListener(eventLogger)
                it.setVideoDebugListener(eventLogger)

                playerView.player = player
                if (isTimelineStatic) {
                    if (playerPosition == C.TIME_UNSET) {
                        it.seekToDefaultPosition(playerWindow)
                    } else {
                        it.seekTo(playerWindow, playerPosition)
                    }
                }
                it.playWhenReady = shouldAutoPlay
                playerNeedsSource = true
            }
        }
        if (playerNeedsSource) {
            val url = GOODGAME_API_HLS + channel + ".smil"
            Log.d(TAG, "initializePlayer: " + url)

            val mediaSource = HlsMediaSource(
                    Uri.parse(url),
                    mediaDataSourceFactory,
                    mainHandler,
                    eventLogger)

            player?.prepare(mediaSource)//, !isTimelineStatic, !isTimelineStatic
            playerNeedsSource = false
            updateButtonVisibilities()
        }
    }

    private fun addToFavorites() {
        Toast.makeText(context, "action_add_fav", Toast.LENGTH_SHORT).show()
    }

    private fun openFullscreen(fullScreen: Boolean) {
        if (fullScreen) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        }
        isFullScreen = !isFullScreen

        emoteIconsKeyboard?.hide()
    }

    private fun showSettings() {
        val fragment = StreamMenuDialogFragment()
        fragment.setSelector(trackSelector)
        fragment.setAdaptiveTrackSelectionFactory(videoTrackSelectionFactory)
        fragment.setTrackInfo(trackSelector?.currentMappedTrackInfo, 0)
        fragment.show(childFragmentManager, fragment.tag)
    }

    private fun updateButtonVisibilities() {
        progressBar.visibility = View.GONE
    }

    private fun releasePlayer() {
        player?.let {
            shouldAutoPlay = it.playWhenReady
            playerWindow = it.currentWindowIndex
            playerPosition = C.TIME_UNSET
            val timeline = it.currentTimeline
            if (!timeline!!.isEmpty && timeline.getWindow(playerWindow, window).isSeekable) {
                playerPosition = it.currentPosition
            }
            it.release()
            player = null
            trackSelector = null
            eventLogger = null
        }
    }

    private fun scrollToBottom() {
        if (isScrollToLast) {
            val firstItemPos = adapter.itemCount
            if (firstItemPos == 0) return
            linearLayoutManager?.scrollToPosition(firstItemPos.minus(1))
        }
    }

    fun onBackPressed() {
        if (emoteIconsKeyboard != null && emoteIconsKeyboard?.isKeyBoardOpen()!!) {
            emoteIconsKeyboard?.hide()
        } else {
            (activity as StreamActivity).close()
        }
    }

    override fun onEmoteIconClick(emoteIcon: EmoteIcon) {
        addEmoteIcon(emoteIcon)
    }

    override fun onEmoteIconLongClick(emoteIcon: EmoteIcon) {
        Toast.makeText(context, R.string.added_to_recent, Toast.LENGTH_SHORT).show()
        repo.addToRecent(emoteIcon)
    }

    override fun onIconRemoveClick(v: View) {
        val event = KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL)
        etMessage.dispatchKeyEvent(event)
    }

    private fun addEmoteIcon(emoteIcon: EmoteIcon) {
        val start: Int  = etMessage.selectionStart - 1
        val end: Int = etMessage.selectionEnd
        var target = EditTextTarget(start, end, etMessage, emoteIcon)
        targets.add(target)

        Picasso.with(context)
                .load(emoteIcon.urls.big)
				.resize(50, 50)
                .placeholder(R.drawable.ic_insert_emoticon)
                .into(target)
    }

    private fun createKeyboard() {
        emoteIconsKeyboard = EmoteIconsKeyboard(context!!,
                contentRoot, this@StreamFragment, repo, picasso)
        emoteIconsKeyboard?.let {
            it.onEmoteIconClickListener = this
            it.onSmileBackspaceClickListener = this
        }
    }

    private fun showKeyboard() {
        emoteIconsKeyboard?.let {
            if (it.isKeyBoardOpen()) {
                it.hide()
            } else {
                etMessage.isFocusableInTouchMode = true
                etMessage.requestFocus()
                it.showAtBottom()
            }
        }

    }


    inner class EditTextTarget(private var start: Int,
                               private var end: Int,
                               private var editText: EditText,
                               var icon: EmoteIcon) : Target {

        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            val bitmapDrawable = BitmapDrawable(editText.context.resources, bitmap)
            bitmapDrawable.setBounds(
                    0,
                    0,
                    bitmapDrawable.intrinsicWidth + 5,
                    bitmapDrawable.intrinsicHeight + 5)
            val imageSpan = ImageSpan(bitmapDrawable, ImageSpan.ALIGN_BOTTOM)

            val message = editText.editableText

            // Insert the smile
            message.replace(start + 1, end, ":" + icon.key + ":")
            message.setSpan(imageSpan, start + 1, start + icon.key.length + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        override fun onBitmapFailed(errorDrawable: Drawable) {
            targets.remove(this)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable) {
            //
        }

    }

    // A method to find height of the status bar
    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    override fun handleErrors(e: Throwable) {
        //
    }

    override fun emoteIconsLoaded(icons: Map<String, EmoteIcon>) {
        createKeyboard()
    }

    override fun onNewMessage(message: GGMessage) {
        adapter.addMessage(message)
        scrollToBottom()
    }
}