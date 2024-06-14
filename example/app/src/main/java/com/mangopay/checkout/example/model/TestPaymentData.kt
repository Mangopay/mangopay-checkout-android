package com.mangopay.checkout.example.model

import com.google.android.gms.wallet.button.ButtonConstants
import com.mangopay.android.core.model.objectclass.Currency
import com.mangopay.android.core.model.paymentmethods.CardParameters
import com.mangopay.android.core.model.paymentmethods.GooglepayButtonOptions
import com.mangopay.android.core.model.paymentmethods.GooglepayObject
import com.mangopay.android.core.model.paymentmethods.ShippingAddressParameters
import com.mangopay.android.core.model.paymentmethods.TransactionInfo
import com.mangopay.android.core.model.request.GooglePayOptions
import com.mangopay.android.core.util.TotalPriceStatus
import com.mangopay.checkout.example.model.request.CreateCardPaymentRequest
import java.util.UUID

object TestPaymentData {

    // Payline info
    // 4970107111111119
    // 4970105181818183
    var cardId = "" // To be updated programmatically
    var profilingAttemptReference = "" // To be updated programmatically
}

fun googlePayConfigTestData(): GooglePayOptions {
    val shippingAddressParametersConfig = ShippingAddressParameters.Builder()
        .phoneNumberRequired(false)
        .allowedCountryCodes(listOf("US", "GB", "FI"))
        .build()

    val transactionInfoConfig = TransactionInfo.Builder()
        .currencyCode(Currency.EUR.currency)
        .countryCode("FI")
        .totalPriceStatus(TotalPriceStatus.FINAL)
        .totalPrice(1500.0)
        .transactionId(UUID.randomUUID().toString())
        .build()

    val cardParametersConfig = CardParameters.Builder()
        .allowedAuthMethods(listOf("PAN_ONLY", "CRYPTOGRAM_3DS"))
        .allowedCardNetworks(listOf("MASTERCARD", "VISA"))
        .build()


    val googlePayObject = GooglepayObject.Builder()
        .merchantName("Mangopay Demo")
        .gateway("whenthen")
        .gatewayMerchantId("gatewayMerchantId_fromMangopay")
        .existingPaymentMethodRequired(true)
        .emailRequired(true)
        .billingAddressRequired(false)
        .shippingOptionRequired(false)
        .shippingAddressRequired(false)
        .shippingAddressParameters(shippingAddressParametersConfig)
        .transactionInfo(transactionInfoConfig)
        .cardParameters(cardParametersConfig)
        .build()

    val gpayButtonOptions = GooglepayButtonOptions.Builder()
        .buttonTheme(ButtonConstants.ButtonTheme.DARK)
        .buttonType(ButtonConstants.ButtonType.CHECKOUT)
        .cornerRadius(5)
        .build()

    return GooglePayOptions.Builder()
        .googlePayObject(googlePayObject)
        .buttonOptions(gpayButtonOptions)
        .build()
}

fun createPayinRequest(cardId: String) = CreateCardPaymentRequest(
    cardId = cardId
)