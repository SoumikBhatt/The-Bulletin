package com.soumik.newsapp.features.home.presentation.ui

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
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
import com.soumik.newsapp.core.utils.DateUtils
import com.soumik.newsapp.core.utils.launchUrl
import com.soumik.newsapp.databinding.NewsDetailsFragmentBinding
import com.soumik.newsapp.core.SharedViewModel
import com.soumik.newsapp.core.utils.Event
import com.squareup.picasso.Picasso

class NewsDetailsFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var mBinding: NewsDetailsFragmentBinding
    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = NewsDetailsFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    override fun onDestroyView() {
        sharedViewModel.apply {
            showBottomNav.value=Event(true)
        }
        super.onDestroyView()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViews() {

        sharedViewModel.showBottomNav.value= Event(false)

        val article = args.article
        mBinding.apply {
            Picasso.get().load(article?.urlToImage).placeholder(R.mipmap.ic_launcher).into(ivNewsImage)
            tvNewsTitle.text = HtmlCompat.fromHtml(article?.title ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvNewsAuthor.text = HtmlCompat.fromHtml(article?.author ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvNewsTime.text = DateUtils.provideFormattedDate("yyyy-MM-dd'T'HH:mm:ss'Z'", article?.publishedAt) + " | "+ DateUtils.provideFormattedTime("yyyy-MM-dd'T'HH:mm:ss'Z'", article?.publishedAt)
            tvNewsContent.text = HtmlCompat.fromHtml(article?.content ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvNewsDescription.text = HtmlCompat.fromHtml(article?.description?:"",HtmlCompat.FROM_HTML_MODE_LEGACY)

            tvNewsContent.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.blue_300
                )
            )
            tvNewsContent.paintFlags = tvNewsContent.paintFlags or Paint.UNDERLINE_TEXT_FLAG

            tvNewsContent.setOnClickListener { requireContext().launchUrl(article?.url) }
            ivBackArrow.setOnClickListener { findNavController().navigateUp() }

            ivFavouriteButton.setOnClickListener {
                ivFavouriteButton.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_bookmark_24))
            }
        }
    }

}