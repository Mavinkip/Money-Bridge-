package com.example.mpesa.daraja

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

    data class PaymentData(
        val phone: String,
        val amount: String,
        val passKey: String,
        val tillNumber: String,
        val callbackUrl: String,
        val consumerKey: String,
        val consumerSecret: String,
        val environment: String
    )

    fun pay(paymentData: PaymentData) {
        viewModelScope.launch {
            isLoading = true
            repository.getAccessToken(
                paymentData.consumerKey,
                paymentData.consumerSecret
            ) { token ->
                if (token != null) {
                    repository.initiateStkPush(
                        token = token,
                        phone = paymentData.phone,
                        amount = paymentData.amount,
                        tillNumber = paymentData.tillNumber,
                        passKey = paymentData.passKey,
                        callbackUrl = paymentData.callbackUrl
                    ) { response ->
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