package com.mangopay.checkout.example.client

import com.mangopay.android.core.model.request.CreateCardRegRestRequest
import com.mangopay.android.core.model.request.CreatePayInRestRequest
import com.mangopay.android.core.model.response.CardRegistrationResponse
import com.mangopay.checkout.example.model.response.OAuthResponse
import com.mangopay.checkout.example.model.response.PayInPaymentResponse
import com.mangopay.checkout.example.model.request.PaypalPaymentRequest
import com.mangopay.checkout.example.model.response.PaypalPaymentResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.basicAuth
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

class KtorAPI(private val client: HttpClient) {
    suspend fun createPaypal(passphrase: String, clientId: String, request: PaypalPaymentRequest): PaypalPaymentResponse {
        val bearerToken = createOAuthToken(passphrase = passphrase, clientId = clientId)
        val response = client.post {
            url("https://api.sandbox.mangopay.com/V2.01/$clientId/payins/payment-methods/paypal")
            setBody(request)
            bearerAuth(bearerToken.accessToken)
        }
        return response.body()
    }
    suspend fun createPayin(passphrase: String, clientId: String, request: CreatePayInRestRequest): PayInPaymentResponse {
        val bearerToken = createOAuthToken(passphrase = passphrase, clientId = clientId)
        val response = client.post {
            url("https://api.sandbox.mangopay.com/V2.01/$clientId/payins/card/direct")
            setBody(request)
            bearerAuth(bearerToken.accessToken)
        }
        return response.body()
    }
    suspend fun createCardRegistrations(passphrase: String, clientId: String, request: CreateCardRegRestRequest): CardRegistrationResponse {
        val bearerToken = createOAuthToken(passphrase = passphrase, clientId = clientId)
        val response = client.post {
            url("https://api.sandbox.mangopay.com/V2.01/$clientId/cardregistrations")
            setBody(request)
            bearerAuth(bearerToken.accessToken)
        }
        return response.body()
    }
    private suspend fun createOAuthToken(passphrase: String, clientId: String): OAuthResponse {
        return client.post {
            url("https://api.sandbox.mangopay.com/V2.01/oauth/token")
            basicAuth(username = clientId, password = passphrase)
        }.body()
    }
}