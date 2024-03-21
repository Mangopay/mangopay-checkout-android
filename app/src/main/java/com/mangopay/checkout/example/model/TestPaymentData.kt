package com.mangopay.checkout.example.model

import com.google.android.gms.wallet.button.ButtonConstants
import com.mangopay.android.core.model.objectclass.Currency
import com.mangopay.android.core.model.paymentmethods.CardParameters
import com.mangopay.android.core.model.paymentmethods.GooglepayButtonOptions
import com.mangopay.android.core.model.paymentmethods.GooglepayObject
import com.mangopay.android.core.model.paymentmethods.ShippingAddressParameters
import com.mangopay.android.core.model.paymentmethods.TransactionInfo
import com.mangopay.android.core.model.request.Address
import com.mangopay.android.core.model.request.BillingShipping
import com.mangopay.android.core.model.request.BrowserInfo
import com.mangopay.android.core.model.request.CreatePayInRestRequest
import com.mangopay.android.core.model.request.FeesCreditedDebitedFunds
import com.mangopay.android.core.model.request.GooglePayOptions
import com.mangopay.android.core.util.TotalPriceStatus
import java.util.UUID

object TestPaymentData {

    // Payline info
    // 4970107111111119
    // 4970105181818183
    var mgpWalletId = "159834019"
    var mgpPassphrase = "7fOfvt3ozv6vkAp1Pahq56hRRXYqJqNXQ4D58v5QCwTocCVWWC"
    var mgpClientId = "checkoutsquatest"
    var mgpUserId = "158091557"
    var mgpAuthorId = "158091557"
    val nethoneMerchantId = 428242
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
        .totalPrice(500.0)
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

fun createPayinRequest() = CreatePayInRestRequest(
    authorId = TestPaymentData.mgpAuthorId,
    billing = BillingShipping(
        address = Address(
            addressLine1 = "3 rue de la Feature",
            addressLine2 = "Bat. MGP",
            postalCode = "75009",
            city = "Paris",
            country = "FR",
            region = "IDF"
        ),
        firstName = "Jide",
        lastName = "Arowofela"
    ),
    browserInfo = BrowserInfo(
        acceptHeader = "text/html, application/xhtml+xml, application/xml;q=0.9, /;q=0.8",
        javaEnabled = true,
        language = "FR-FR",
        colorDepth = 4,
        screenHeight = 1800,
        screenWidth = 400,
        timeZoneOffset = 60,
        userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 13_6_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148",
        javascriptEnabled = true
    ),
    cardId = TestPaymentData.cardId,
    creditedUserId = TestPaymentData.mgpUserId,
    creditedWalletId = TestPaymentData.mgpWalletId,
    debitedFunds = FeesCreditedDebitedFunds(
        currency = "EUR",
        amount = 10
    ),
    fees = FeesCreditedDebitedFunds(
        currency = "EUR",
        amount = 0
    ),
    ipAddress = "159.180.248.187",
    secureModeReturnURL = "https://mangopay.com/",
    shipping = BillingShipping(
        address = Address(
            addressLine1 = "3 rue de la Feature",
            addressLine2 = "Bat. MGP",
            postalCode = "75009",
            city = "Paris",
            country = "FR",
            region = "IDF"
        ),
        firstName = "Jide",
        lastName = "Arowofela"
    ),
    statementDescriptor = "MANGOPAY",
    tag = "Created using MANGOPAY API Collection Android Sdk",
    profilingAttemptReference = TestPaymentData.profilingAttemptReference,
    culture = "EN"
)