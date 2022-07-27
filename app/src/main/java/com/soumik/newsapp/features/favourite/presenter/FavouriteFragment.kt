package com.soumik.newsapp.features.favourite.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nybsys.sentok.core.customViews.FullScreenViewType
import com.soumik.newsapp.core.utils.*
import com.soumik.newsapp.databinding.FavouriteFragmentBinding
import com.soumik.newsapp.features.home.domain.model.Article
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    @Inject
    lateinit var mViewModel: FavouriteViewModel
    private lateinit var mBinding: FavouriteFragmentBinding
    private val mAdapter: FavouriteListAdapter by lazy {
        FavouriteListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FavouriteFragmentBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setViews()
        setObservers()

    }

    private fun init() {
        mViewModel.fetchFavouriteList()
    }

    private fun setViews() {
        mBinding.apply {
            rvFavNews.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = mAdapter
            }
        }

        mAdapter.apply {
            onItemClicked {
                findNavController().navigate(
                    FavouriteFragmentDirections.actionDestFavouriteToDestNewsDetails(
                        Article(
                            author = it.author, content = it.content,
                            description = it.description, publishedAt = it.publishedAt,
                            source = null, title = it.title,
                            urlToImage = it.urlToImage, url = it.url
                        ), it.category
                    )
                )
            }
            onFavouriteItemClicked {
                mViewModel.deleteFavouriteList(it)
            }
        }
    }

    private fun setObservers() {
        mViewModel.apply {
            loading.observe(viewLifecycleOwner) {
                mBinding.hideFullScreenView()
                mBinding.showLoadingView(it)
            }
            errorMessage.observe(viewLifecycleOwner) {
                mBinding.showErrorView(it)
            }
            favouriteList.observe(viewLifecycleOwner) {
                mBinding.hideFullScreenView()
                mAdapter.submitList(it)
            }
        }
    }

    private fun FavouriteFragmentBinding.hideFullScreenView() {
        fullScreenView.apply {
            gone()
            hide(FullScreenViewType.LoadingView)
            hide(FullScreenViewType.ErrorView)
            hide(FullScreenViewType.NoItemView)
        }
    }

    private fun FavouriteFragmentBinding.showErrorView(it: String?) {
        fullScreenView.visible()
        when (it) {
            Constants.NO_ITEM_FOUND -> fullScreenView.show(FullScreenViewType.NoItemView)
            else -> fullScreenView.show(FullScreenViewType.ErrorView)
        }
//        fullScreenView.hide(FullScreenViewType.LoadingView)
//        fullScreenView.hide(FullScreenViewType.ErrorView)
    }

    private fun FavouriteFragmentBinding.showLoadingView(it: Boolean) {
        rvFavNews.handleVisibility(!it)
        shimmerProgress.handleShimmer(it)
        shimmerProgress.handleVisibility(it)
    }

}