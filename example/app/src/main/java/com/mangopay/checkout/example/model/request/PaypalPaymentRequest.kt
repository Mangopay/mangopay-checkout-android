package com.mangopay.checkout.example.model.request

import kotlinx.serialization.Serializable

@Serializable
class PaypalPaymentRequest

fun createPaypalRequest() = PaypalPaymentRequest()