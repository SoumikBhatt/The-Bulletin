package com.soumik.newsapp.features.favourite.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumik.newsapp.NewsApp
import com.soumik.newsapp.core.utils.handleShimmer
import com.soumik.newsapp.core.utils.handleVisibility
import com.soumik.newsapp.databinding.FavouriteFragmentBinding
import com.soumik.newsapp.features.home.domain.model.Article
import javax.inject.Inject

class FavouriteFragment : Fragment() {

    @Inject lateinit var mViewModel: FavouriteViewModel
    private lateinit var mBinding: FavouriteFragmentBinding
    private val mAdapter : FavouriteListAdapter by lazy {
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
        (requireActivity().application as NewsApp).appComponent.inject(this)

        init()
        setViews()
        setObservers()

    }

    private fun init() {
        mViewModel.fetchFavouriteListCo()
    }

    private fun setViews() {
        mBinding.apply {
            rvFavNews.apply{
                layoutManager = LinearLayoutManager(requireContext())
                adapter = mAdapter
            }
        }

        mAdapter.apply {
            onItemClicked {
                findNavController().navigate(FavouriteFragmentDirections.actionDestFavouriteToDestNewsDetails(
                    Article(author = it.author, content = it.content, description = it.description, publishedAt = it.publishedAt, source = null, title = it.title, urlToImage = it.urlToImage, url = it.url)
                ))
            }
            onFavouriteItemClicked {
                mViewModel.deleteFavouriteList(it)
            }
        }
    }

    private fun setObservers() {
        mViewModel.apply {
            loading.observe(viewLifecycleOwner) {
                showLoadingView(it)
            }
            errorMessage.observe(viewLifecycleOwner) {
                mBinding.apply {
                    rvFavNews.visibility=View.GONE
                    tvErrorMessage.visibility=View.VISIBLE
                    tvErrorMessage.text=it
                }
            }
            favouriteList.observe(viewLifecycleOwner) {
                mAdapter.submitList(it)
            }
        }
    }

    private fun showLoadingView(it: Boolean) {
        mBinding.apply {
            rvFavNews.handleVisibility(!it)
            shimmerProgress.handleShimmer(it)
            shimmerProgress.handleVisibility(it)
        }
    }

}