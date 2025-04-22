package com.example.mpesa

import android.util.Base64
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DarajaRepository {
    private val client = OkHttpClient()
    private val consumerKey = "ERsF7OBVpGAJ6cPnAsD4zTDUXCkaAXslwPS6BtUHP8EJJkMy"
    private val consumerSecret = "m3FxQMh0rWyaV8rQB4npW1rByU7OquuXpH48dERwq9w3t9XuLsDgsrsaenai2OI0"
    private val shortCode = "174379"
    private val passKey = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"
    private val callbackUrl = "https://yourdomain.com/callback"

    fun getAccessToken(onResult: (String?) -> Unit) {
        val credentials = "$consumerKey:$consumerSecret"
        val encoded = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        val request = Request.Builder()
            .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
            .get()
            .addHeader("Authorization", "Basic $encoded")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onResult(null)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val json = JSONObject(response.body?.string() ?: "")
                    onResult(json.optString("access_token"))
                } catch (e: Exception) {
                    onResult(null)
                }
            }
        })
    }

    fun initiateStkPush(token: String, phone: String, amount: String, onResponse: (String) -> Unit) {
        try {
            // Validate inputs
            if (phone.length < 10 || amount.toDoubleOrNull() == null) {
                onResponse("Invalid phone number or amount")
                return
            }

            val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
            val password = Base64.encodeToString("$shortCode$passKey$timestamp".toByteArray(), Base64.NO_WRAP)

            val stkPush = JSONObject().apply {
                put("BusinessShortCode", shortCode)
                put("Password", password)
                put("Timestamp", timestamp)
                put("TransactionType", "CustomerPayBillOnline")
                put("Amount", amount)
                put("PartyA", phone)
                put("PartyB", shortCode)
                put("PhoneNumber", phone)
                put("CallBackURL", callbackUrl)
                put("AccountReference", "TestPayment")
                put("TransactionDesc", "Payment")
            }

            val body = stkPush.toString().toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest")
                .post(body)
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse("Network error: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    onResponse(response.body?.string() ?: "Empty response")
                }
            })
        } catch (e: Exception) {
            onResponse("Error: ${e.message}")
        }
    }
}