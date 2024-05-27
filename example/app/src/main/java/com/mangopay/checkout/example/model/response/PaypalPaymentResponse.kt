package com.mangopay.checkout.example.model.response

import com.mangopay.android.core.model.request.FeesCreditedDebitedFunds
import com.mangopay.android.core.model.request.LineItemRequest
import com.mangopay.checkout.example.model.request.LineItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaypalPaymentResponse(

    @SerialName("Id")
    val id: String,

    @SerialName("Tag")
    val tag: String,

    @SerialName("CreationDate")
    val creationDate: Int,

    @SerialName("AuthorId")
    val authorId: String,

    @SerialName("DebitedFunds")
    val debitedFunds: FeesCreditedDebitedFunds?,

    @SerialName("CreditedFunds")
    val creditedFunds: FeesCreditedDebitedFunds,

    @SerialName("Fees")
    val fees: FeesCreditedDebitedFunds?,

    @SerialName("Status")
    val status: String,

    @SerialName("ResultCode")
    val resultCode: String?,

    @SerialName("ResultMessage")
    val resultMessage: String?,

    @SerialName("ExecutionDate")
    val executionDate: Int?,

    @SerialName("Type")
    val type: String,

    @SerialName("Nature")
    val nature: String,

    @SerialName("CreditedUserId")
    val creditedUserId: String?,

    @SerialName("CreditedWalletId")
    val              creditedWalletId: String?,

    @SerialName("PaymentType")
    val paymentType: String,

    @SerialName("ExecutionType")
    val executionType: String,

    @SerialName("RedirectURL")
    val redirectURL: String?,

    @SerialName("ReturnURL")
    val returnURL: String?,

    @SerialName("StatementDescriptor")
    val statementDescriptor: String?,

    @SerialName("Culture")
    val culture: String,

    @SerialName("ShippingPreference")
    val shippingPreference: String?,

    @SerialName("PaypalBuyerAccountEmail")
    val paypalBuyerAccountEmail: String?,

    @SerialName("Reference")
    val reference: String?,

    @SerialName("Trackings")
    val trackings: String?,

    @SerialName("LineItems")
    val lineItems: List<LineItem>
)

fun PaypalPaymentResponse.toPaymentImpl() = PaymentImpl(
    id = this.id,
    status = this.status,
    returnURL = this.returnURL.orEmpty(),
    redirectURL = this.redirectURL.orEmpty()
)

fun LineItem.toLineItemRequest() = LineItemRequest.Builder()
    .description(this.description)
    .taxAmount(this.taxAmount)
    .name(this.name)
    .quantity(this.quantity)
    .unitAmount(this.unitAmount)
    .build()

