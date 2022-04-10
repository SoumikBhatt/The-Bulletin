package com.soumik.newsapp.features.favourite.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.soumik.newsapp.R
import com.soumik.newsapp.core.base.BaseAdapter
import com.soumik.newsapp.databinding.ItemFavouriteNewsBinding
import com.soumik.newsapp.features.favourite.domain.entity.Favourite

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
class FavouriteListAdapter : BaseAdapter<Favourite, ItemFavouriteNewsBinding>(
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

    override fun createBinding(parent: ViewGroup): ItemFavouriteNewsBinding {
        return ItemFavouriteNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    }

    override fun bind(binding: ItemFavouriteNewsBinding, item: Favourite,position:Int) {
        binding.apply {
            tvAuthor.text = item.author
            tvTitle.text = item.title

            root.setOnClickListener { onItemClicked?.let { it(item) } }
            ivFavouriteButton.setOnClickListener {
                onFavouriteItemClicked?.let { it(item) }
                ivFavouriteButton.setBackgroundResource(R.drawable.ic_favorite_border_24)
            }
        }
    }

    fun onItemClicked(listener: (Favourite)->Unit) {onItemClicked=listener}
    fun onFavouriteItemClicked(listener: (Favourite)->Unit) {onFavouriteItemClicked=listener}
}