package com.ukrida.mygithubapplication.ui.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ukrida.mygithubapplication.settings.SettingsPreference
import kotlinx.coroutines.launch

class SettingsViewModel (private val pref: SettingsPreference) : ViewModel() {
    fun getTheme(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveTheme(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}