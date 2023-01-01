package com.soumik.newsapp.core.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

open class BaseViewHolder<out T : ViewBinding> constructor(open val binding: T) :
    RecyclerView.ViewHolder(binding.root)


open class BaseAdViewHolder<out T : ViewBinding, out A : ViewBinding?> constructor(
    binding: T?,
    adBinding: A?
) : RecyclerView.ViewHolder(
    binding?.root ?: adBinding!!.root
)

class ItemViewHolder<out T : ViewBinding, out A : ViewBinding> constructor(val binding: T?) :
    BaseAdViewHolder<T, A>(binding, null)

class AdViewHolder<out T : ViewBinding, out A : ViewBinding> constructor(val adBinding: A?) :
    BaseAdViewHolder<T, A>(null, adBinding)