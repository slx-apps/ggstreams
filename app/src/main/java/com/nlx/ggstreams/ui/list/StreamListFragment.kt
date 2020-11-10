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
import com.nlx.ggstreams.databinding.FrStreamListBinding
import com.nlx.ggstreams.ui.MainActivity
import com.nlx.ggstreams.ui.list.adapter.StreamsAdapter
import com.squareup.picasso.Picasso
import com.trello.rxlifecycle2.components.support.RxFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StreamListFragment : RxFragment() {

    private var _binding: FrStreamListBinding? = null
    private val binding get() = _binding!!

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FrStreamListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as MainActivity).setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)

        val spans = resources.getInteger(R.integer.stream_list_spans)

        binding.swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)


        binding.swipeContainer.setOnRefreshListener {
            loadStreams()
        }

        val gridLayoutManager = androidx.recyclerview.widget.GridLayoutManager(context, spans)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvStreamList.layoutManager = gridLayoutManager
        binding.rvStreamList.adapter = adapter

        binding.rvStreamList.itemAnimator = DefaultItemAnimator()
        binding.rvStreamList.setHasFixedSize(false)

        lifecycleScope.launch {
            model.listLiveData().collectLatest { pagingData ->
                binding.swipeContainer.isRefreshing = false
                adapter.submitData(pagingData)
            }
        }
    }

    private fun loadStreams() {
        binding.swipeContainer.isRefreshing = true

        model.invalidateList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}