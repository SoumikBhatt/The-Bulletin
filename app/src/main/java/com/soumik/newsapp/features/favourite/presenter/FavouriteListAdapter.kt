package com.soumik.newsapp.features.favourite.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.soumik.newsapp.NewsApp
import com.soumik.newsapp.R
import com.soumik.newsapp.core.ads.NativeAdMob
import com.soumik.newsapp.core.base.BaseAdAdapter
import com.soumik.newsapp.databinding.ItemFavouriteNewsBinding
import com.soumik.newsapp.databinding.ItemNativeAdBinding
import com.soumik.newsapp.features.favourite.domain.entity.Favourite

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
class FavouriteListAdapter : BaseAdAdapter<Favourite, ItemFavouriteNewsBinding,ItemNativeAdBinding>(
    diffCallback = object : ItemCallback<Favourite>() {
        override fun areItemsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
            return oldItem == newItem
        }
    }
) {

    private var onItemClicked : ((Favourite)->Unit) ? =null
    private var onFavouriteItemClicked : ((Favourite)->Unit) ? =null

    override fun createItemBinding(parent: ViewGroup): ItemFavouriteNewsBinding {
        return ItemFavouriteNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    }

    override fun crateAdsBinding(parent: ViewGroup): ItemNativeAdBinding {
        return ItemNativeAdBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    }

    override fun bindItems(binding: ItemFavouriteNewsBinding?, item: Favourite?, position: Int) {
        binding?.apply {
            tvAuthor.text = item?.author
            tvTitle.text = item?.title

            root.setOnClickListener { onItemClicked?.let {
                if (item != null) {
                    it(item)
                }
            } }
            ivFavouriteButton.setOnClickListener {
                onFavouriteItemClicked?.let {
                    if (item != null) {
                        it(item)
                    }
                }
                ivFavouriteButton.setBackgroundResource(R.drawable.ic_favorite_border_24)
            }
        }
    }

    override fun bindAds(binding: ItemNativeAdBinding?, item: Favourite?, position: Int) {
        binding?.apply {
            NativeAdMob.showNativeAd(NewsApp.mContext,adSmallTemplate)
        }
    }

    fun onItemClicked(listener: (Favourite)->Unit) {onItemClicked=listener}
    fun onFavouriteItemClicked(listener: (Favourite)->Unit) {onFavouriteItemClicked=listener}
}