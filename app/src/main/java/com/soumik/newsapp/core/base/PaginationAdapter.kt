package com.soumik.newsapp.core.base

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
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

    companion object {
        private const val ITEMS = 0
        private const val LOADER = 1
    }

    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        return when (viewType) {
            ITEMS -> {
                val binding = itemBinding(parent)
                ItemViewHolder(binding)
            }
            else -> {
                val binding = loaderBinding(parent)
                LoaderViewHolder(binding)
            }
        }
    }

    protected abstract fun itemBinding(parent: ViewGroup): V
    protected abstract fun loaderBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        when (getItemViewType(position)) {
            ITEMS -> {
                val itemViewHolder: ItemViewHolder<V> = holder as ItemViewHolder<V>
                bindItems(itemViewHolder.binding, getItem(position), position)
            }
            LOADER -> {
                val loaderViewHolder: LoaderViewHolder<V> = holder as LoaderViewHolder<V>
                bindLoader(loaderViewHolder.binding, getItem(position), position)
            }
        }
    }

    protected abstract fun bindItems(binding: V, item: T, position: Int)
    protected abstract fun bindLoader(binding: V, item: T, position: Int)


    override fun getItemViewType(position: Int): Int {
        return if(position==currentList.size - 1 && isLoadingAdded) LOADER else ITEMS
    }

    fun addLoader() {
        isLoadingAdded = true
        submitList(emptyList())
//        addLoadingFooter()
    }

    fun removeLoader() {
        isLoadingAdded = false
        val position = currentList.size-1
        val result = getItem(position)

        if (result!=null) {
            currentList.remove(result)
            notifyItemRemoved(position)
        }
    }

    protected abstract fun addLoadingFooter()
}