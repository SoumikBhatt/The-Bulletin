package com.soumik.newsapp.core.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

open class BaseViewHolder<out T: ViewBinding> constructor(open val binding: T) : RecyclerView.ViewHolder(binding.root)

class ItemViewHolder<out T: ViewBinding> constructor(override val binding: T)  : BaseViewHolder<T>(binding)
class LoaderViewHolder<out T: ViewBinding> constructor(override val binding: T) : BaseViewHolder<T>(binding)