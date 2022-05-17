package com.soumik.newsapp.features.home.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.soumik.newsapp.R
import com.soumik.newsapp.databinding.ItemNewsBinding
import com.soumik.newsapp.features.home.domain.model.Article
import com.squareup.picasso.Picasso

/**
created by Soumik on 17/5/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
class PagedNewsListAdapter : PagingDataAdapter<Article, RecyclerView.ViewHolder>(
    diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
) {

    private var onItemClicked : ((Article)->Unit) ? =null
    private var onFavouriteItemClicked : ((Article)->Unit) ? =null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PagedNewsListHolder) {
            holder.bind(getItem(position),position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PagedNewsListHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    inner class PagedNewsListHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article?,position: Int) {
            binding.apply {
                Picasso.get().load(item?.urlToImage).placeholder(R.mipmap.ic_launcher).into(ivImage)
                tvAuthor.text = HtmlCompat.fromHtml(item?.author?:"",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                tvDescription.text = HtmlCompat.fromHtml(item?.description?:"",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                tvTitle.text = HtmlCompat.fromHtml(item?.title?:"", HtmlCompat.FROM_HTML_MODE_LEGACY)

                root.setOnClickListener { onItemClicked?.let { it(item!!) }}

                ivFavouriteButton.setOnClickListener {
                    onFavouriteItemClicked?.let { it(item!!) }
                    ivFavouriteButton.setBackgroundResource(R.drawable.ic_favorite_24)
                }
            }
        }
    }

    fun onItemClicked(listener: ((Article)->Unit)) {
        onItemClicked = listener
    }

    fun onFavouriteItemClicked(listener: (Article) -> Unit) {
        onFavouriteItemClicked = listener
    }

}