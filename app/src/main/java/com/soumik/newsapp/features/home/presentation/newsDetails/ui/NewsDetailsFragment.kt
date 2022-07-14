package com.soumik.newsapp.features.home.presentation.newsDetails.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.soumik.newsapp.NewsApp
import com.soumik.newsapp.R
import com.soumik.newsapp.core.SharedViewModel
import com.soumik.newsapp.core.ads.BannerAd
import com.soumik.newsapp.core.ads.InterstitialAdMob
import com.soumik.newsapp.core.utils.DateUtils
import com.soumik.newsapp.core.utils.Event
import com.soumik.newsapp.core.utils.loadImage
import com.soumik.newsapp.databinding.NewsDetailsFragmentBinding
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import com.soumik.newsapp.features.home.domain.model.Article
import com.soumik.newsapp.features.home.presentation.newsDetails.viewmodel.NewsDetailsViewModel
import javax.inject.Inject

class NewsDetailsFragment : Fragment() {

    companion object {
        private const val TAG = "NewsDetailsFragment"
    }

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: NewsDetailsFragmentBinding? = null
    private var mBinding: NewsDetailsFragmentBinding? = _binding
    private val args: NewsDetailsFragmentArgs by navArgs()
    private var isFullNewsClicked = false

    @Inject
    lateinit var mViewModel: NewsDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = NewsDetailsFragmentBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as NewsApp).appComponent.inject(this)
        BannerAd.loadBanner(requireActivity(), mBinding!!.bannerAdContainer)

        init()
        setUpObservers()
        setUpViews()
    }

    private fun setUpObservers() {
        mViewModel.apply {
            isFavourite.observe(viewLifecycleOwner) {
                if (it) {
                    mBinding?.ivFavouriteButton?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_bookmark_24
                        )
                    )
                } else {
                    mBinding?.ivFavouriteButton?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_bookmark_border_24
                        )
                    )
                }
            }
        }
    }

    private fun init() {
        mViewModel.apply {
            checkIfFavourite(args.article?.title!!, args.category!!, args.article?.author)
        }
    }

    override fun onDestroyView() {
        sharedViewModel.apply {
            showBottomNav.value = Event(!isFullNewsClicked)
        }

        BannerAd.removeBanner(mBinding!!.bannerAdContainer)
        BannerAd.destroy()

        _binding = null

        super.onDestroyView()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViews() {

        sharedViewModel.showBottomNav.value = Event(false)
        isFullNewsClicked = false

        val article = args.article
        mBinding?.apply {
            if (article?.urlToImage != null && article.urlToImage!!.isNotEmpty()) {
                ivNewsImage.loadImage(
                    url = article.urlToImage!!,
                    placeHolder = ContextCompat.getDrawable(
                        ivNewsImage.context,
                        R.mipmap.ic_launcher
                    )
                )
            }
            tvNewsTitle.text =
                HtmlCompat.fromHtml(article?.title ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvNewsAuthor.text =
                HtmlCompat.fromHtml(article?.author ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvNewsTime.text = DateUtils.provideFormattedDate(
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                article?.publishedAt
            ) + " | " + DateUtils.provideFormattedTime(
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                article?.publishedAt
            )
            tvNewsContent.text =
                HtmlCompat.fromHtml(article?.content ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvNewsDescription.text =
                HtmlCompat.fromHtml(article?.description ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)

            btnFullNews.setOnClickListener { showFullNews(article) }
            ivBackArrow.setOnClickListener { findNavController().navigateUp() }

            ivFavouriteButton.setOnClickListener {
                mViewModel.apply {
                    Log.d(TAG, "setUpViews: isFav: ${isFavourite.value}")
                    if (isFavourite.value == true) {
                        mViewModel.deleteFavouriteList(
                            Favourite(
                                author = article?.author,
                                content = article?.content,
                                description = article?.description,
                                publishedAt = article?.publishedAt,
                                title = article?.title,
                                url = article?.url,
                                urlToImage = article?.urlToImage,
                                category = args.category,
                                isFavourite = 1
                            )
                        )
                    } else {
                        mViewModel.insertFavouriteItem(
                            Favourite(
                                author = article?.author,
                                content = article?.content,
                                description = article?.description,
                                publishedAt = article?.publishedAt,
                                title = article?.title,
                                url = article?.url,
                                urlToImage = article?.urlToImage,
                                category = args.category,
                                isFavourite = 1
                            )
                        )
                    }
                }
            }
        }
    }

    private fun showFullNews(article: Article?) {
        isFullNewsClicked = true

        InterstitialAdMob.loadInterstitialAd(
            activity = requireActivity(),
            interstitialAdController = object : InterstitialAdMob.InterstitialAdController {
                override fun onAddDismissed() {
                    //        requireContext().launchUrl(article?.url)
                    findNavController().navigate(
                        NewsDetailsFragmentDirections.actionDestNewsDetailsToDestWebView(
                            article?.title,
                            article?.url
                        )
                    )
                }
            })
    }

}