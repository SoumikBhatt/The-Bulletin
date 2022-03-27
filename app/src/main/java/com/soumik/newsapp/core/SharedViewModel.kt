package com.soumik.newsapp.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soumik.newsapp.core.utils.Event

/**
created by Soumik on 27/3/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
class SharedViewModel : ViewModel() {
    private val _showBottomNav = MutableLiveData<Event<Boolean>>()
    val showBottomNav get() = _showBottomNav
}