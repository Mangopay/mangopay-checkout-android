package com.mangopay.checkout.example.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateCardPaymentRequest(
    val cardId: String,
)