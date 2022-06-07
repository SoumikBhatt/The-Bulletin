package com.soumik.newsapp.core.customViews

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.nybsys.sentok.core.customViews.FullScreenViewType
import com.soumik.newsapp.R
import com.soumik.newsapp.core.utils.gone
import com.soumik.newsapp.core.utils.isGone
import com.soumik.newsapp.core.utils.isVisible
import com.soumik.newsapp.core.utils.visible
import kotlinx.android.synthetic.main.full_screen_view.view.*

/**
created by Soumik on 7/6/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/


/*
* FullScreenView is used for showing LOADING or ERROR VIEW
*/
class FullScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(
            context,
            R.layout.full_screen_view,
            this
        )
    }

    fun show(type: FullScreenViewType) {
        when (type) {
            FullScreenViewType.LoadingView -> {
                if (error_view.isVisible() || error_text.isVisible()) {
                    error_view.gone()
                    error_text.gone()
                }
                if (no_item_view.isVisible()) {
                    no_item_view.gone()
                }
                if (loader.isGone()) {
                    loader.visible()
                }
            }
            FullScreenViewType.ErrorView -> {
                if (loader.isVisible()) {
                    loader.gone()
                }
                if (no_item_view.isVisible()) {
                    no_item_view.gone()
                }
                if (error_view.isGone()) {
                    error_view.visible()
                }
                if (error_text.isGone()) {
                    error_text.visible()
                }
            }
            FullScreenViewType.NoItemView -> {
                if (loader.isVisible()) {
                    loader.gone()
                }
                if (error_view.isVisible() || error_text.isVisible()) {
                    error_view.gone()
                    error_text.gone()
                }
                if (no_item_view.isGone()) {
                    no_item_view.visible()
                }
            }
        }
    }

    fun hide(type: FullScreenViewType) {
        when (type) {
            FullScreenViewType.LoadingView -> {
                if (loader.isVisible()) {
                    loader.gone()
                }
            }
            FullScreenViewType.ErrorView -> {
                if (error_view.isVisible()) {
                    error_view.gone()
                }
                if (error_text.isVisible()) {
                    error_text.gone()
                }
            }
            FullScreenViewType.NoItemView -> {
                if (no_item_view.isVisible()) {
                    no_item_view.gone()
                }
            }
        }
    }
}