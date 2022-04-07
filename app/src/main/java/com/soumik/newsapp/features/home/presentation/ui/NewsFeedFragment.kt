package com.soumik.newsapp.features.home.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumik.newsapp.NewsApp
import com.soumik.newsapp.R
import com.soumik.newsapp.core.utils.Messenger
import com.soumik.newsapp.databinding.FragmentNewsFeedBinding
import com.soumik.newsapp.features.home.presentation.HomeFragmentDirections
import com.soumik.newsapp.features.home.presentation.viewmodel.NewsFeedViewModel
import javax.inject.Inject

class NewsFeedFragment : Fragment() {

    companion object {
        private const val ARG_CATEGORY = "category"

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
            adapter = mNewsListAdapter
        }

        mNewsListAdapter.apply {
            onItemClicked {
                findNavController().navigate(
                    HomeFragmentDirections.actionDestHomeToDestNewsDetails(
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

        mViewModel.fetchTopHeadlines("us", category)

        mBinding.swipeRefresh.setOnRefreshListener {
            mViewModel.fetchTopHeadlines("us", category)
        }

    }
}