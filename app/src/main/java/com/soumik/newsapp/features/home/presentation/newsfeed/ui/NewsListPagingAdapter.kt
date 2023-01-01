package com.soumik.newsapp.features.home.presentation.newsfeed.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.DiffUtil
import com.soumik.newsapp.R
import com.soumik.newsapp.core.ads.NativeAdMob
import com.soumik.newsapp.core.base.BaseAdPagingAdapter
import com.soumik.newsapp.core.utils.loadImage
import com.soumik.newsapp.databinding.ItemNativeAdBinding
import com.soumik.newsapp.databinding.ItemNewsBinding
import com.soumik.newsapp.features.home.domain.model.Article
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
created by Soumik on 22/3/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
class NewsListPagingAdapter @Inject constructor(@ActivityContext private val context: Context) :
    BaseAdPagingAdapter<Article, ItemNewsBinding, ItemNativeAdBinding>(
        diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }
    ) {

    private var onItemClicked: ((Article) -> Unit)? = null

    override fun createItemBinding(parent: ViewGroup): ItemNewsBinding {
        return ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun crateAdsBinding(parent: ViewGroup): ItemNativeAdBinding {
        return ItemNativeAdBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bindItems(binding: ItemNewsBinding?, item: Article?, position: Int) {
        binding?.apply {
            if (item?.urlToImage != null && item.urlToImage!!.isNotEmpty()) {
                ivImage.loadImage(
                    url = item.urlToImage!!,
                    placeHolder = ContextCompat.getDrawable(ivImage.context, R.mipmap.ic_launcher)
                )
                tvAuthor.text = HtmlCompat.fromHtml(item.author ?: "", FROM_HTML_MODE_LEGACY)
            }

            tvDescription.text = HtmlCompat.fromHtml(item?.description ?: "", FROM_HTML_MODE_LEGACY)
            tvTitle.text = HtmlCompat.fromHtml(item?.title ?: "", FROM_HTML_MODE_LEGACY)

            root.setOnClickListener {
                onItemClicked?.let {
                    if (item != null) {
                        it(item)
                    }
                }
            }
        }
    }

    override fun bindAds(binding: ItemNativeAdBinding?, item: Article?, position: Int) {
        binding?.apply {
            NativeAdMob.showNativeAd(context, adSmallTemplate)
        }
    }

    fun onItemClicked(listener: ((Article) -> Unit)) {
        onItemClicked = listener
    }
}