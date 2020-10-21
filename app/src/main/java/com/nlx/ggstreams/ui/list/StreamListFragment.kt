package com.nlx.ggstreams.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlx.ggstreams.R
import com.nlx.ggstreams.ui.MainActivity
import com.nlx.ggstreams.ui.list.adapter.StreamsAdapter
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

    private val navigation by lazy {
        findNavController()
    }

    private val adapter by lazy {
        StreamsAdapter(requireContext(), picasso) {
            val action = StreamListFragmentDirections
                    .actionStreamListFragmentToStreamFragment(
                        item = it
                    )
            navigation.navigate(action)
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

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as MainActivity).setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)

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

    private fun loadStreams() {
        swipeContainer.isRefreshing = true

        model.invalidateList()
    }
}