package com.soumik.newsapp.core.base

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

/**
created by Soumik on 14/7/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/

abstract class BaseAdAdapter<T : Any, V : ViewBinding, A : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseAdViewHolder<V, A>>(AsyncDifferConfig.Builder(diffCallback).build()) {

    companion object {
        private const val ITEMS = 0
        private const val AD = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdViewHolder<V, A> {
        return when (viewType) {
            ITEMS -> {
                val binding = createItemBinding(parent)
                ItemViewHolder(binding)
            }
            else -> {
                val binding = crateAdsBinding(parent)
                AdViewHolder(binding)
            }
        }
    }

    protected abstract fun createItemBinding(parent: ViewGroup): V
    protected abstract fun crateAdsBinding(parent: ViewGroup): A


    protected abstract fun bindItems(binding: V?, item: T?, position: Int)
    protected abstract fun bindAds(binding: A?, item: T?, position: Int)

    override fun onBindViewHolder(holder: BaseAdViewHolder<V, A>, position: Int) {
        when (getItemViewType(position)) {
            ITEMS -> {
                val itemViewHolder = holder as ItemViewHolder<V, A>
                bindItems(itemViewHolder.binding, getItem(position), position)
            }
            AD -> {
                val adViewHolder = holder as AdViewHolder<V, A>
                bindAds(adViewHolder.adBinding, getItem(position), position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount - 1 && position % 4 == 0 && position != 0) AD
        else ITEMS
    }

}