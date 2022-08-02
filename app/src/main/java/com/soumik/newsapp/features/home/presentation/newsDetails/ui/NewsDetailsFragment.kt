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
import com.soumik.newsapp.R
import com.soumik.newsapp.core.SharedViewModel
import com.soumik.newsapp.core.ads.BannerAd
import com.soumik.newsapp.core.ads.InterstitialAdMob
import com.soumik.newsapp.core.ads.RewardedAdMob
import com.soumik.newsapp.core.utils.DateUtils
import com.soumik.newsapp.core.utils.Event
import com.soumik.newsapp.core.utils.loadImage
import com.soumik.newsapp.core.utils.share
import com.soumik.newsapp.databinding.NewsDetailsFragmentBinding
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import com.soumik.newsapp.features.home.domain.model.Article
import com.soumik.newsapp.features.home.presentation.newsDetails.viewmodel.NewsDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
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
        BannerAd.loadBanner(requireActivity(), mBinding!!.bannerAdContainer)

        init()
        setUpObservers()
        setUpViews()
        setViewListeners()
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
        }
    }

    private fun setViewListeners() {

        val article = args.article

        mBinding?.apply {
            btnFullNews.setOnClickListener { showFullNews(article) }
            ivBackArrow.setOnClickListener { findNavController().navigateUp() }

            ivFavouriteButton.setOnClickListener {
                mViewModel.apply {
                    Log.d(TAG, "setUpViews: isFav: ${isFavourite.value}")
                    if (isFavourite.value == true) {
                        deleteFavouriteItem(article)
                    } else {
                        showRewardedAd(article)
                    }
                }
            }

            ivShareNews.setOnClickListener {
                requireContext().share(subject = article?.title, body = "${article?.content}\n\n${article?.url}")
            }
        }
    }

    private fun deleteFavouriteItem(article: Article?) {
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
    }

    private fun insertFavouriteItem(article: Article?) {
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

    private fun showFullNews(article: Article?) {
        isFullNewsClicked = true
        showInterstitialAd(article)
    }

    private fun showInterstitialAd(article: Article?) {
        InterstitialAdMob.loadInterstitialAd(
            activity = requireActivity(),
            interstitialAdController = object : InterstitialAdMob.InterstitialAdController {
                override fun onAddDismissed() {
                    navigateToDetailsWeb(article)
                }

                override fun onAdUnready() {
                    navigateToDetailsWeb(article)
                }
            })
    }

    private fun showRewardedAd(article: Article?) {
        RewardedAdMob.loadRewardedAd(
            activity = requireActivity(),
            rewardedAdController = object : RewardedAdMob.RewardedAdController {
                override fun onAdDismissed() {
                    insertFavouriteItem(article)
                }

                override fun onAdUnready() {
                    insertFavouriteItem(article)
                }
            })
    }

    private fun navigateToDetailsWeb(article: Article?) {
        findNavController().navigate(
            //        requireContext().launchUrl(article?.url)
            NewsDetailsFragmentDirections.actionDestNewsDetailsToDestWebView(
                article?.title,
                article?.url
            )
        )
    }

}