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
import com.nybsys.sentok.core.customViews.FullScreenViewType
import com.soumik.newsapp.NewsApp
import com.soumik.newsapp.R
import com.soumik.newsapp.core.ads.NativeAdMob
import com.soumik.newsapp.core.utils.gone
import com.soumik.newsapp.core.utils.handleVisibility
import com.soumik.newsapp.core.utils.visible
import com.soumik.newsapp.databinding.FragmentNewsFeedBinding
import com.soumik.newsapp.features.home.presentation.HomeFragmentDirections
import com.soumik.newsapp.features.home.presentation.newsfeed.viewmodel.NewsFeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
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

    @Inject lateinit var mNewsListAdapter : NewsListPagingAdapter

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

        init()
        setViews()
        setObservers()
        observeLoadingState()
    }

    private fun setObservers() {
        mViewModel.apply {
            pagedNewsData.observe(viewLifecycleOwner) {
                Log.d(TAG, "setObservers: observing pagedNewsData")
                mBinding.apply {
                    hideFullScreenView()
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
                Log.d(TAG, "setObservers: Error: $it")
                mBinding.showErrorView()
            }

            loading.observe(viewLifecycleOwner) {
                Log.d(TAG, "setObservers: Loading: $it")
                mBinding.showLoadingView(it)
            }

        }
    }

    private fun FragmentNewsFeedBinding.hideFullScreenView() {
        fullScreenView.apply {
            gone()
            hide(FullScreenViewType.LoadingView)
            hide(FullScreenViewType.ErrorView)
            hide(FullScreenViewType.NoItemView)
        }
    }

    private fun FragmentNewsFeedBinding.showLoadingView(it: Boolean) {
        if (it) {
            hideFullScreenView()
            rvNews.visibility = View.GONE
            shimmerProgress.visibility = View.VISIBLE
            shimmerProgress.startShimmer()
        } else {
            shimmerProgress.visibility = View.GONE
            shimmerProgress.stopShimmer()
        }
    }

    private fun FragmentNewsFeedBinding.showErrorView() {
        fullScreenView.visible()
        swipeRefresh.isRefreshing = false
        prependProgress.handleVisibility(false)
        appendProgress.handleVisibility(false)
        rvNews.handleVisibility(false)
        fullScreenView.show(FullScreenViewType.ErrorView)
    }

    /*
     * observing loading state of the adapter
     */
    private fun observeLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mNewsListAdapter.loadStateFlow.collect {
                    mBinding.apply {
                        prependProgress.isVisible = it.source.prepend is LoadState.Loading
                        appendProgress.isVisible = it.source.append is LoadState.Loading
                        if (it.source.refresh is LoadState.Loading && mNewsListAdapter.itemCount == 0) {
                            mViewModel.showLoader(true)
                        } else {
                            mViewModel.showLoader(false)
                            mBinding.rvNews.visibility = View.VISIBLE
                        }
                        if (it.source.refresh is LoadState.Error) {
                            mBinding.showErrorView()
                        }
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
        mViewModel.fetchTopHeadlines("us", category, 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        try {
            NativeAdMob.destroy()
        } catch (e: Exception) {
        }
    }
}