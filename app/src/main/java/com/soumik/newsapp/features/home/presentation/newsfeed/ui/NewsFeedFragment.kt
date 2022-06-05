package com.soumik.newsapp.features.home.presentation.newsfeed.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumik.newsapp.NewsApp
import com.soumik.newsapp.R
import com.soumik.newsapp.core.utils.Messenger
import com.soumik.newsapp.databinding.FragmentNewsFeedBinding
import com.soumik.newsapp.features.home.presentation.HomeFragmentDirections
import com.soumik.newsapp.features.home.presentation.newsfeed.viewmodel.NewsFeedViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedFragment : Fragment() {

    companion object {
        private const val ARG_CATEGORY = "category"
        private const val TAG = "NewsFeedFragment"
        fun newInstance(category: String): NewsFeedFragment {
            val fragment = NewsFeedFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }

    }

    private var category: String? = null


    @Inject
    lateinit var mViewModel: NewsFeedViewModel
    private lateinit var mBinding: FragmentNewsFeedBinding
    private val mNewsListAdapter: NewsListAdapter by lazy {
        NewsListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentNewsFeedBinding.inflate(inflater, container, false)

        if (arguments != null) category = requireArguments().getString(ARG_CATEGORY)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as NewsApp).appComponent.inject(this)

        init()
        setViews()
        setObservers()
    }

    private fun setObservers() {
        mViewModel.apply {
            newsData.observe(viewLifecycleOwner) {
                Log.d(TAG, "setObservers: Here")
                mBinding.rvNews.visibility = View.VISIBLE
                mBinding.swipeRefresh.isRefreshing = false
//                mNewsListAdapter.submitList(it)
            }

            pagedNewsData.observe(viewLifecycleOwner) {
                Log.d(TAG, "setObservers: observing pagedNewsData")
                mBinding.apply {
                    rvNews.visibility = View.VISIBLE
                    swipeRefresh.isRefreshing = false
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        mNewsListAdapter.submitData(it)
                    }
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                mBinding.swipeRefresh.isRefreshing = false
                Messenger.showSnackBar(mBinding, it)
            }

            loading.observe(viewLifecycleOwner) {
                Log.d(TAG, "setObservers: Loading: $it")
                if (it) {
                    mBinding.rvNews.visibility = View.GONE
                    mBinding.shimmerProgress.visibility = View.VISIBLE
                    mBinding.shimmerProgress.startShimmer()
                } else {
                    mBinding.shimmerProgress.visibility = View.GONE
                    mBinding.shimmerProgress.stopShimmer()
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mNewsListAdapter.loadStateFlow.collect {
                    mBinding.apply {
                        prependProgress.isVisible = it.source.prepend is LoadState.Loading
                        appendProgress.isVisible = it.source.append is LoadState.Loading
                    }
                }
            }
        }
    }

    private fun setViews() {
        Log.d(TAG, "setViews: ")
        mBinding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mNewsListAdapter
        }

        mNewsListAdapter.apply {
            onItemClicked {
                findNavController().navigate(
                    HomeFragmentDirections.actionDestHomeToDestNewsDetails(
                        it, category
                    )
                )
            }
        }

        mBinding.swipeRefresh.setColorSchemeResources(
            R.color.red,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
    }

    private fun init() {
        fetchNews()

        mBinding.swipeRefresh.setOnRefreshListener {
            fetchNews()
        }

    }

    private fun fetchNews() {
        mViewModel.fetchPagedTopHeadlines("us", category, 1)
    }
}