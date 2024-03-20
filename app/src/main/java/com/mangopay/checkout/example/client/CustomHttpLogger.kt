package com.mangopay.checkout.example.client

import android.util.Log
import io.ktor.client.plugins.logging.Logger

class CustomHttpLogger : Logger {
    override fun log(message: String) {
        Log.d("HTTP_MESSAGE", message)
    }
}