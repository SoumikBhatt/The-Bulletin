package com.soumik.newsapp.features.topHeadlines.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.soumik.newsapp.core.base.BaseAdapter
import com.soumik.newsapp.databinding.ItemNewsBinding
import com.soumik.newsapp.features.topHeadlines.data.model.Article
import com.squareup.picasso.Picasso

/**
created by Soumik on 22/3/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
class TopHeadlinesAdapter : BaseAdapter<Article, ItemNewsBinding>(
    diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
) {
    override fun createBinding(parent: ViewGroup): ItemNewsBinding {
        return ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(binding: ItemNewsBinding, item: Article) {
        binding.apply {
            Picasso.get().load(item.urlToImage).into(ivImage)
            tvAuthor.text = item.author
            tvDescription.text = item.description
            tvTitle.text = item.title
        }
    }
}