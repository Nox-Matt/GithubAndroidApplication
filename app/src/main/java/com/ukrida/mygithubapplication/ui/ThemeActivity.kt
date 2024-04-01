package com.ukrida.mygithubapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.ukrida.mygithubapplication.R
import com.ukrida.mygithubapplication.settings.SettingsPreference
import com.ukrida.mygithubapplication.settings.dataStore
import com.ukrida.mygithubapplication.ui.Adapters.SettingsViewModelFactory
import com.ukrida.mygithubapplication.ui.ViewModels.SettingsViewModel

class ThemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingsPreference.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref))[SettingsViewModel::class.java]
        settingViewModel.getTheme().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveTheme(isChecked)
        }
    }
}