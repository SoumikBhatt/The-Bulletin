package com.soumik.newsapp.features.news_feed.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumik.newsapp.NewsApp
import com.soumik.newsapp.R
import com.soumik.newsapp.core.utils.Connectivity
import com.soumik.newsapp.core.utils.Messenger
import com.soumik.newsapp.databinding.NewsFeedFragmentBinding
import com.soumik.newsapp.features.news_feed.presentation.viewmodel.HomeViewModel
import javax.inject.Inject

class NewsFeedFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    @Inject
    lateinit var mViewModel: HomeViewModel

    @Inject
    lateinit var mConnectivity: Connectivity
    private lateinit var mBinding: NewsFeedFragmentBinding

    private val mTopHeadlinesAdapter: TopHeadlinesAdapter by lazy {
        TopHeadlinesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = NewsFeedFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (requireActivity().application as NewsApp).appComponent.inject(this)

        init()
        setViews()
        setObservers()
    }

    private fun setObservers() {
        mViewModel.apply {
            newsData.observe(viewLifecycleOwner) {
                Log.d(TAG, "setObservers: NewsData: ${it?.articles?.size}")
                if (it?.status == "ok") {
                    if (!it.articles.isNullOrEmpty()) {
                        mBinding.rvNews.visibility = View.VISIBLE
                        mBinding.swipeRefresh.isRefreshing = false
                        mTopHeadlinesAdapter.submitList(it.articles)
                    } else mTopHeadlinesAdapter.submitList(emptyList())
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                mBinding.swipeRefresh.isRefreshing = false
                Messenger.showSnackBar(mBinding, it)
            }

            loading.observe(viewLifecycleOwner) {
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
    }

    private fun setViews() {
        mBinding.rvNews.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mTopHeadlinesAdapter
        }

        mTopHeadlinesAdapter.apply {
            onItemClicked {
                findNavController().navigate(
                    NewsFeedFragmentDirections.actionDestHomeToDestNewsDetails(
                        it
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
        if (mConnectivity.hasInternetConnection()) {
            mViewModel.fetchTopHeadlines("us")
        }

        mBinding.swipeRefresh.setOnRefreshListener {
            if (mConnectivity.hasInternetConnection()) mViewModel.fetchTopHeadlines("us")
            else mBinding.swipeRefresh.isRefreshing=false
        }

    }

}