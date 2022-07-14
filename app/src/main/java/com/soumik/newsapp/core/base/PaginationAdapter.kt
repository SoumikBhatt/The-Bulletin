package com.soumik.newsapp.core.base

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

/**
created by Soumik on 26/5/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/

abstract class BasePagingAdapter<T : Any,V:ViewBinding>(
    diffCallback : DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, BaseViewHolder<V>>(
    diffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        val binding = createBinding(parent)
        return BaseViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup) : V

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        getItem(position)?.let { bind(holder.binding, it,position) }
    }

    protected abstract fun bind(binding:V,item:T,position: Int)
}