package com.nlx.ggstreams.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nlx.ggstreams.App
import com.nlx.ggstreams.R
import com.nlx.ggstreams.di.ViewModelFactory
import com.nlx.ggstreams.list.adapter.StreamsAdapter
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.stream.StreamActivity
import com.nlx.ggstreams.stream.StreamActivity.Companion.KEY_STREAM
import com.nlx.ggstreams.utils.rx.RxUtils
import com.squareup.picasso.Picasso
import com.trello.rxlifecycle2.components.support.RxFragment
import kotlinx.android.synthetic.main.fr_stream_list.*
import javax.inject.Inject

private const val TAG = "StreamListFragment"

class StreamListFragment : RxFragment() {

    @Inject
    lateinit var picasso: Picasso
    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var rxUtils: RxUtils

    private lateinit var model: StreamListViewModel

    private val adapter by lazy {
        StreamsAdapter(context!!, picasso) {
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

    override fun onAttach(context: Context) {
        (activity?.application as App).appComponent.streamListComponent().create().inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fr_stream_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProviders.of(this, factory).get(StreamListViewModel::class.java)

        val spans = resources.getInteger(R.integer.stream_list_spans)

        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)


        swipeContainer.setOnRefreshListener {
            loadStreams(true)
        }

        val gridLayoutManager = androidx.recyclerview.widget.GridLayoutManager(context, spans)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvStreamList.layoutManager = gridLayoutManager
        rvStreamList.adapter = adapter

        rvStreamList.itemAnimator = DefaultItemAnimator()
        rvStreamList.setHasFixedSize(false)

        model.listLiveData().observe(this, Observer {
            swipeContainer.isRefreshing = false
            adapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fr_stream_list, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_refresh) {
            loadStreams(true)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadStreams(refresh: Boolean) {
        swipeContainer.isRefreshing = refresh

        model.invalidateList()
    }

    fun streamListLoaded(list: List<GGStream>, refresh: Boolean) {
        Log.d(TAG, "streamListLoaded->onNext")

        if (refresh) {
            swipeContainer.isRefreshing = false
        }
        adapter.notifyDataSetChanged()
    }

    fun handleErrors(e: Throwable) {
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