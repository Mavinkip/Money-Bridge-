package com.example.mpesa.daraja

import android.util.Base64
import com.example.mpesa.connection.RetrofitClient
import com.example.mpesa.data.AccessTokenResponse

import com.example.mpesa.data.StkPushRequest
import com.example.mpesa.data.StkPushResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DarajaRepository {
    private val api = RetrofitClient.instance

    fun getAccessToken(consumerKey: String, consumerSecret: String, onResult: (String?) -> Unit) {
        val credentials = "$consumerKey:$consumerSecret"
        val encoded = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        val authHeader = "Basic $encoded"

        api.getAccessToken(authHeader).enqueue(object : Callback<AccessTokenResponse> {
            override fun onResponse(
                call: Call<AccessTokenResponse>,
                response: Response<AccessTokenResponse>
            ) {
                if (response.isSuccessful) {
                    onResult(response.body()?.access_token)
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun initiateStkPush(
        token: String,
        phone: String,
        amount: String,
        tillNumber:String,
        passKey: String,
        callbackUrl: String,
        onResponse: (String) -> Unit
    ) {
        val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        val password =
            Base64.encodeToString("$tillNumber$passKey$timestamp".toByteArray(), Base64.NO_WRAP)

        val request = StkPushRequest(
            BusinessShortCode = tillNumber,
            Password = password,
            Timestamp = timestamp,
            Amount = amount,
            PartyA = phone,
            PartyB = tillNumber,
            PhoneNumber = phone,
            CallBackURL = callbackUrl,
            AccountReference = "Pos-Payment",
            TransactionDesc = "Store Purchase"
        )

        api.initiateStkPush("Bearer $token", request).enqueue(object : Callback<StkPushResponse> {
            override fun onResponse(
                call: Call<StkPushResponse>,
                response: Response<StkPushResponse>
            ) {
                if (response.isSuccessful) {
                    onResponse(response.body()?.CustomerMessage ?: "Request successful")
                } else {
                    onResponse("Failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StkPushResponse>, t: Throwable) {
                onResponse("Error: ${t.message}")
            }
        })
    }


}

