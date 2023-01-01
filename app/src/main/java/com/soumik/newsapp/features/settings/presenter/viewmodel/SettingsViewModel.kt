package com.soumik.newsapp.features.settings.presenter.viewmodel

import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _darkThemeEnabled : MutableLiveData<Boolean> = MutableLiveData()
    val darkThemeEnabled : LiveData<Boolean> get() = _darkThemeEnabled

    fun checkAppTheme(resources: Resources?) {
        _darkThemeEnabled.value =
            (resources?.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK) != Configuration.UI_MODE_NIGHT_NO
    }

    fun switchAppTheme(resources: Resources?) {
        val mode = if ((resources?.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
            Configuration.UI_MODE_NIGHT_NO
        ) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }
}