package com.nlx.ggstreams.keyboard

import android.app.Activity
import android.content.Context
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.PopupWindow
import com.nlx.ggstreams.R
import com.nlx.ggstreams.data.EmoteIconsRepo
import com.nlx.ggstreams.keyboard.EmoteIconsKeyboard
import com.nlx.ggstreams.keyboard.EmoteIconsTabView
import com.nlx.ggstreams.keyboard.OnEmoteIconClickListener
import com.nlx.ggstreams.models.EmoteIcon
import java.lang.ref.WeakReference
import java.util.ArrayList
import com.squareup.picasso.Picasso

class EmoteIconsKeyboard(val context: Context,
                         val contentRoot: View,
                         var onEmoteIconClickListener: OnEmoteIconClickListener,
                         val repo: EmoteIconsRepo,
                         val picasso: Picasso) : PopupWindow() {

    private var isOpened = false

    var onSmileBackspaceClickListener: OnIconRemoveClickListener? = null
    private var onSoftKeyboardOpenCloseListener: OnSoftKeyboardOpenCloseListener? = null


    init {
        val view:View = createView()
        contentView = view

        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE

        setSize(700, WindowManager.LayoutParams.MATCH_PARENT)

        animationStyle = R.style.KeyboardAnimation
    }

    fun hide() {
        isOpened = false
        dismiss()
    }

    fun showAtBottom() {
        isOpened = true
        showAtLocation(contentRoot, Gravity.BOTTOM, 0, 0)
    }

    private fun createView(): View {
        val inflater = context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.emote_icons_keyboard, null)

        val tabs = view.findViewById<TabLayout>(R.id.emote_icons_tabs)
        val pager = view.findViewById<androidx.viewpager.widget.ViewPager>(R.id.pager)
        val ibBackspace = view.findViewById<ImageButton>(R.id.ibBackspace)

        ibBackspace.setOnClickListener { v ->
                onSmileBackspaceClickListener?.onIconRemoveClick(v)
        }

        val views = ArrayList<EmoteIconsTabView>()
        //List<IconTab> iconTabs = new ArrayList<>();

        val emoteIcons = ArrayList<EmoteIcon>()
        val recent = ArrayList<EmoteIcon>()

        emoteIcons.addAll(repo.getEmoteIconsList())
        recent.addAll(repo.getRecent())

        Log.d(TAG, "createView: " + emoteIcons.size)

        views.add(EmoteIconsTabView(context, emoteIcons, this@EmoteIconsKeyboard, picasso))
        views.add(EmoteIconsTabView(context, recent, this@EmoteIconsKeyboard, picasso))

        val tabAdapter = TabPagerAdapter(views)


        pager.adapter = tabAdapter
        tabs.setupWithViewPager(pager)
        tabs.tabGravity = TabLayout.GRAVITY_FILL
        tabs.tabMode = TabLayout.MODE_FIXED

        return view
    }

    private inner class TabPagerAdapter(private val views: List<EmoteIconsTabView>?) : androidx.viewpager.widget.PagerAdapter() {

        override fun getPageTitle(position: Int): CharSequence {
            if (position == 0) {
                return context.getString(R.string.tab_free_title)
            } else if (position == 1) {
                return context.getString(R.string.tab_recent_title)
            } else {
                return ""
            }
        }

        override fun getCount(): Int {
            return views?.size ?: 0
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val v = views!![position].rootView
            container.addView(v, 0)
            return v
        }

        override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
            container.removeView(view as View)
        }

        override fun isViewFromObject(view: View, key: Any): Boolean {
            return key === view
        }
    }

    fun setSize(height: Int, width: Int) {
        setWidth(width)
        setHeight(height)
    }

    fun isKeyBoardOpen(): Boolean {
        return isOpened
    }

    interface OnIconRemoveClickListener {
        fun onIconRemoveClick(v: View)
    }

    interface OnSoftKeyboardOpenCloseListener {
        fun onKeyboardOpen(keyBoardHeight: Int)
        fun onKeyboardClose()
    }

    companion object {
        const val TAG = "EmoteIconsKeyboard"
    }
}
