package com.mangopay.checkout.example.di

import com.mangopay.checkout.example.client.KtorAPI
import com.mangopay.checkout.example.client.RestApiClient
import org.koin.dsl.module

val demoAppModule = module {
    single { RestApiClient.get() }
    single { KtorAPI(get()) }
}