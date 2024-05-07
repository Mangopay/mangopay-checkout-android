package com.mangopay.checkout.example.client

import com.mangopay.android.core.exception.MangopayApiException
import com.mangopay.android.core.model.response.MangoErrorResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.json.Json

object RestApiClient {

    fun get(): HttpClient {
        return HttpClient(Android) {
            expectSuccess = true
            install(Logging) {
                logger = CustomHttpLogger()
                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                headers.append("Content-Type", "application/json")
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 100000
            }
            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    when (exception) {
                        is UnresolvedAddressException -> throw MangopayApiException("Error connecting to the internet")
                        is ClientRequestException -> {
                            val exceptionResponse = exception.response
                            val errorResponse: MangoErrorResponse? = exceptionResponse.body()
                            throw MangopayApiException(
                                errorResponse?.message ?: "Invalid API response"
                            )
                        }
                        else -> {
                            throw MangopayApiException(exception.message.orEmpty(), exception)
                        }
                    }

                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}