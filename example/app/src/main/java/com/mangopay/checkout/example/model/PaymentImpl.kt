package com.mangopay.checkout.example.model

import com.mangopay.android.core.model.objectclass.Payment

class PaymentImpl(
    private val id: String,
    private val status: String,
    private val returnURL: String?,
    private val redirectURL: String?,
): Payment {
    override fun id(): String {
        return id
    }

    override fun status(): String {
        return status
    }

    override fun redirectURL(): String? {
        return redirectURL
    }

    override fun returnURL(): String? {
        return returnURL
    }
}