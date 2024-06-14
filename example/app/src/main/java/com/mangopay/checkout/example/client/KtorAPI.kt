package com.mangopay.checkout.example.client

import com.mangopay.android.core.model.request.CreateCardRegRestRequest
import com.mangopay.android.core.model.response.CardRegistrationResponse
import com.mangopay.checkout.example.model.Constants
import com.mangopay.checkout.example.model.request.CreateCardPaymentRequest
import com.mangopay.checkout.example.model.response.PayInPaymentResponse
import com.mangopay.checkout.example.model.request.PaypalPaymentRequest
import com.mangopay.checkout.example.model.response.PaypalPaymentResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

class KtorAPI(private val client: HttpClient) {
    suspend fun createPaypal(request: PaypalPaymentRequest): PaypalPaymentResponse {
        val response = client.post {
            url("${Constants.EXAMPLE_BACKEND_URL}/create-paypal-payment")
            setBody(request)
        }
        return response.body()
    }
    suspend fun createPayin(request: CreateCardPaymentRequest): PayInPaymentResponse {
        val response = client.post {
            url("${Constants.EXAMPLE_BACKEND_URL}/create-card-payment")
            setBody(request)
        }
        return response.body()
    }
    suspend fun createCardRegistrations(request: CreateCardRegRestRequest): CardRegistrationResponse {
        val response = client.post {
            url("${Constants.EXAMPLE_BACKEND_URL}/card-registration")
            setBody(request)
        }
        return response.body()
    }
}