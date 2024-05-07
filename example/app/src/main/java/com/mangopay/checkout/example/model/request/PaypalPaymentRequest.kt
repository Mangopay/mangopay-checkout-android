package com.mangopay.checkout.example.model.request

import com.mangopay.android.core.model.request.Address
import com.mangopay.checkout.example.model.TestPaymentData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaypalPaymentRequest(
    @SerialName("ShippingPreference")
    val shippingPreference: String,

    @SerialName("Tag")
    val tag: String,

    @SerialName("ShippingAddress")
    val shippingAddress: ShippingAddress,

    @SerialName("ReturnURL")
    val returnURL: String,

    @SerialName("Reference")
    val reference: String,

    @SerialName("Culture")
    val culture: String,

    @SerialName("CreditedWalletId")
    val creditedWalletId: String,

    @SerialName("AuthorId")
    val authorId: String,

    @SerialName("DebitedFunds")
    val debitedFunds: FeesCreditedDebitedFunds,

    @SerialName("Fees")
    val fees: FeesCreditedDebitedFunds,

    @SerialName("LineItems")
    val lineItems: List<LineItem>,

    @SerialName("ProfilingAttemptReference")
    val profilingAttemptReference: String
)

@Serializable
data class ShippingAddress(
    @SerialName("Address")
    val address: Address,
    @SerialName("RecipientName")
    val recipientName: String
)

@Serializable
data class Address(
    @SerialName("AddressLine1")
    val addressLine1: String?,
    @SerialName("AddressLine2")
    val addressLine2: String?,
    @SerialName("City")
    val city: String?,
    @SerialName("Country")
    val country: String?,
    @SerialName("PostalCode")
    val postalCode: String?,
    @SerialName("Region")
    val region: String?
)

@Serializable
data class FeesCreditedDebitedFunds(
    @SerialName("Amount")
    val amount: Int?,
    @SerialName("Currency")
    val currency: String?
)

@Serializable
data class LineItem(
    @SerialName("Description")
    val description: String,

    @SerialName("TaxAmount")
    val taxAmount: Int,

    @SerialName("Name")
    val name: String,

    @SerialName("Quantity")
    val quantity: Int,

    @SerialName("UnitAmount")
    val unitAmount: Int,
)

fun createPaypalRequest() = PaypalPaymentRequest(
    shippingPreference = "GET_FROM_FILE",
    tag = "Postman create a payin paypal",
    shippingAddress = ShippingAddress(
        address = Address(
            addressLine1 = "3 rue de la Feature",
            addressLine2 = "Bat. MGP",
            postalCode = "75009",
            city = "Paris",
            country = "FR",
            region = "IDF"
        ),
        recipientName = "John Doe"
    ),
    returnURL = "https://mangopay.com/",
    reference = "123-456",
    culture = "fr",
    creditedWalletId = TestPaymentData.mgpWalletId,
    authorId = TestPaymentData.mgpAuthorId,
    debitedFunds = FeesCreditedDebitedFunds(
        currency = "EUR",
        amount = 200
    ),
    fees = FeesCreditedDebitedFunds(
        currency = "EUR",
        amount = 0
    ),
    lineItems = listOf(
        LineItem(
            description = "sub-seller information",
            taxAmount = 0,
            name = "running shoe 1",
            quantity = 1,
            unitAmount = 200
        )
    ),
    profilingAttemptReference = TestPaymentData.profilingAttemptReference
)