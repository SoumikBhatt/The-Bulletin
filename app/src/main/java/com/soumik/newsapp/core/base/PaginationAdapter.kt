package com.soumik.newsapp.core.base

import android.view.ViewGroup
import androidx.paging.DifferCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

/**
created by Soumik on 26/5/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
abstract class PaginationAdapter<T, V : ViewBinding>(
    differCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<V>>(
    AsyncDifferConfig.Builder(differCallback).build()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        //TODO CREATE HOLDER
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}