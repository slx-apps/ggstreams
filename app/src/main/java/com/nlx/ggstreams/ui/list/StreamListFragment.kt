package com.nlx.ggstreams.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nlx.ggstreams.R
import com.nlx.ggstreams.ui.list.adapter.StreamsAdapter
import com.nlx.ggstreams.ui.stream.StreamActivity
import com.nlx.ggstreams.ui.stream.StreamActivity.Companion.KEY_STREAM
import com.nlx.ggstreams.utils.rx.RxUtils
import com.squareup.picasso.Picasso
import com.trello.rxlifecycle2.components.support.RxFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fr_stream_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StreamListFragment : RxFragment() {

    @Inject
    lateinit var picasso: Picasso

    private val model: StreamListViewModel by viewModels { defaultViewModelProviderFactory }

    private val adapter by lazy {
        StreamsAdapter(requireContext(), picasso) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_stream_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spans = resources.getInteger(R.integer.stream_list_spans)

        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)


        swipeContainer.setOnRefreshListener {
            loadStreams()
        }

        val gridLayoutManager = androidx.recyclerview.widget.GridLayoutManager(context, spans)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvStreamList.layoutManager = gridLayoutManager
        rvStreamList.adapter = adapter

        rvStreamList.itemAnimator = DefaultItemAnimator()
        rvStreamList.setHasFixedSize(false)

        lifecycleScope.launch {
            model.listLiveData().collectLatest { pagingData ->
                swipeContainer.isRefreshing = false
                adapter.submitData(pagingData)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fr_stream_list, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_refresh) {
            loadStreams()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadStreams() {
        swipeContainer.isRefreshing = true

        model.invalidateList()
    }

    fun handleErrors(e: Throwable) {
        showError(e.localizedMessage)
    }

    private fun showError(message: String) {
        Snackbar.make(swipeContainer, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.reload) {
                    loadStreams()
                }
                .show()
    }
}