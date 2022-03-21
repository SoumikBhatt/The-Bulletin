package com.soumik.newsapp.base

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

abstract class BaseAdapter<T,V:ViewBinding>(
    diffCallback : DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<V>>(
    AsyncDifferConfig.Builder(diffCallback).build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        val binding = createBinding(parent)
        return BaseViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup) : V

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        bind(holder.binding,getItem(position))
    }

    protected abstract fun bind(binding:V,item:T)
}