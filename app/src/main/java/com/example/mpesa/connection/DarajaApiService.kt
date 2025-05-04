package com.example.mpesa.connection

import com.example.mpesa.data.AccessTokenResponse

import com.example.mpesa.data.StkPushRequest
import com.example.mpesa.data.StkPushResponse
import retrofit2.Call
import retrofit2.http.*

interface DarajaApiService {
    @GET("oauth/v1/generate?grant_type=client_credentials")
    fun getAccessToken(
        @Header("Authorization") authHeader: String
    ): Call<AccessTokenResponse>

    @POST("mpesa/stkpush/v1/processrequest")
    fun initiateStkPush(
        @Header("Authorization") bearerToken: String,
        @Body request: StkPushRequest
    ): Call<StkPushResponse>

}