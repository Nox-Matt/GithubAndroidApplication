package com.ukrida.mygithubapplication.ui.Adapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ukrida.mygithubapplication.settings.SettingsPreference
import com.ukrida.mygithubapplication.ui.ViewModels.SettingsViewModel

class SettingsViewModelFactory (private val pref: SettingsPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel: " + modelClass.name)
    }
}