package com.nlx.ggstreams.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.Toast
import com.nlx.ggstreams.EndlessRecyclerViewScrollListener
import com.nlx.ggstreams.R
import com.nlx.ggstreams.list.mvp.StreamListModel
import com.nlx.ggstreams.list.adapter.StreamsAdapter
import com.nlx.ggstreams.list.mvp.StreamListMVP
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.models.StreamListResponse
import com.nlx.ggstreams.stream.KEY_STREAM
import com.nlx.ggstreams.stream.StreamActivity
import com.squareup.picasso.Picasso
import com.trello.rxlifecycle2.components.support.RxFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fr_stream_list.*
import java.util.*
import javax.inject.Inject

private const val TAG = "StreamListFragment"

class StreamListFragment : RxFragment(), StreamListMVP.View {

    @Inject
    lateinit var picasso: Picasso
    @Inject
    lateinit var presenter: StreamListMVP.Presenter

    private val adapter by lazy {
        StreamsAdapter(context, picasso) {
            val intent = Intent(activity, StreamActivity::class.java)
            intent.putExtra(KEY_STREAM, it)
            startActivity(intent)
        }
    }

    companion object {
        fun newInstance(): StreamListFragment {
            val newsFragment = StreamListFragment()
            val args = Bundle()
            newsFragment.arguments = args
            return newsFragment
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fr_stream_list, container, false)
        return view
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spans = resources.getInteger(R.integer.stream_list_spans)

        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)


        swipeContainer.setOnRefreshListener {
            loadStreams(true)
        }

        val gridLayoutManager = GridLayoutManager(context, spans)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvStreamList.layoutManager = gridLayoutManager
        rvStreamList.adapter = adapter

        rvStreamList.itemAnimator = DefaultItemAnimator()
        rvStreamList.setHasFixedSize(false)
        rvStreamList.addOnScrollListener(object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                Log.d(TAG, "onLoadMore() called with: page = [$page], totalItemsCount = [$totalItemsCount]")

                loadStreams(false)
            }
        })

        loadStreams(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_fr_stream_list, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId

        if (id == R.id.action_refresh) {
            loadStreams(true)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadStreams(refresh: Boolean) {
        swipeContainer.isRefreshing = refresh

        presenter.loadStreams(refresh)
    }

    override fun streamListLoaded(list: List<GGStream>, refresh: Boolean) {
        Log.d(TAG, "streamListLoaded->onNext")

        if (refresh) {
            swipeContainer.isRefreshing = false
        }

        adapter.setList(list, refresh)
        adapter.notifyDataSetChanged()
    }

    override fun handleErrors(e: Throwable) {
        showError(e.localizedMessage)
    }

    private fun showError(message: String) {
        Snackbar.make(swipeContainer, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.reload) {
                    loadStreams(true)
                }
                .show()
    }
}