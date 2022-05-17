package com.soumik.newsapp.features.home.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumik.newsapp.NewsApp
import com.soumik.newsapp.R
import com.soumik.newsapp.core.utils.Messenger
import com.soumik.newsapp.databinding.FragmentNewsFeedBinding
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import com.soumik.newsapp.features.home.presentation.HomeFragmentDirections
import com.soumik.newsapp.features.home.presentation.viewmodel.NewsFeedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
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
    private val mPagedNewsListAdapter : PagedNewsListAdapter by lazy {
        PagedNewsListAdapter()
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
                mNewsListAdapter.submitList(it)
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
//            adapter = mNewsListAdapter
            adapter = mPagedNewsListAdapter
        }

        mPagedNewsListAdapter.apply {
            onItemClicked {
                findNavController().navigate(
                    HomeFragmentDirections.actionDestHomeToDestNewsDetails(
                        it
                    )
                )
            }
            onFavouriteItemClicked {
                mViewModel.insertFavouriteItem(Favourite(author = it.author, content = it.content, description = it.description, publishedAt = it.publishedAt, title = it.title, url = it.url, urlToImage = it.urlToImage, category = category, isFavourite = 1))
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

        fetchTopHeadlines()

        mBinding.swipeRefresh.setOnRefreshListener {
            fetchTopHeadlines()
        }

    }

    private fun fetchTopHeadlines() {
//        mViewModel.fetchTopHeadlines("us", category, 1)
        lifecycleScope.launch {
            mViewModel.fetchTopHeadlinesPage("us",category,1).distinctUntilChanged().collectLatest {
                Log.d(TAG, "fetchTopHeadlines: $it")
                mBinding.rvNews.visibility = View.VISIBLE
                mBinding.swipeRefresh.isRefreshing = false
                mPagedNewsListAdapter.submitData(it)
            }
        }

    }
}