package com.example.mpesa.data

// AccessTokenResponse.kt
data class AccessTokenResponse(
    val access_token: String,
    val expires_in: String
)

// StkPushRequest.kt
data class StkPushRequest(
    val BusinessShortCode: String,  // Your Till Number (e.g., "123456")
    val Password: String,          // Generated password (no change)
    val Timestamp: String,         // Current timestamp (no change)
    val TransactionType: String = "CustomerBuyGoodsOnline", // Changed!
    val Amount: String,
    val PartyA: String,            // Customer phone (254...)
    val PartyB: String,            // Your Till Number (same as BusinessShortCode)
    val PhoneNumber: String,       // Customer phone (254...)
    val CallBackURL: String,
    val AccountReference: String,  // e.g., "Order123"
    val TransactionDesc: String    // e.g., "Payment for goods"
)

// StkPushResponse.kt
data class StkPushResponse(
    val MerchantRequestID: String?,
    val CheckoutRequestID: String?,
    val ResponseCode: String?,
    val ResponseDescription: String?,
    val CustomerMessage: String?
)
