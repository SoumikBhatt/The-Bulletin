package com.nybsys.sentok.core.customViews

sealed class FullScreenViewType {
    object ErrorView : FullScreenViewType()
    object LoadingView : FullScreenViewType()
    object NoItemView : FullScreenViewType()
}
