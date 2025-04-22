package com.example.mpesa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DarajaViewModel : ViewModel() {
    private val repository = DarajaRepository()
    var resultMessage by mutableStateOf("")
        private set
    var isLoading by mutableStateOf(false)
        private set

    fun pay(phone: String, amount: String) {
        viewModelScope.launch {
            isLoading = true
            repository.getAccessToken { token ->
                if (token != null) {
                    repository.initiateStkPush(token, phone, amount) { response ->
                        resultMessage = response
                        isLoading = false
                    }
                } else {
                    resultMessage = "Failed to get access token"
                    isLoading = false
                }
            }
        }
    }
}