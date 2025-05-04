package com.example.mpesa.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mpesa.BuildConfig

class SettingsViewModel : ViewModel() {
    fun saveSettings() {
        TODO("Not yet implemented")
    }

    var consumerKey by mutableStateOf(BuildConfig.DARAJA_CONSUMER_KEY)
    var consumerSecret by mutableStateOf(BuildConfig.DARAJA_CONSUMER_SECRET)
    var passKey by mutableStateOf(BuildConfig.DARAJA_PASS_KEY)
    var shortCode by mutableStateOf(BuildConfig.DARAJA_SHORT_CODE)
    var callbackUrl by mutableStateOf(BuildConfig.DARAJA_CALLBACK_URL)
    var environment by mutableStateOf(BuildConfig.DARAJA_ENVIRONMENT)
}
