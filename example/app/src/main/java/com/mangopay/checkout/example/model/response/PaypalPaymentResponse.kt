package com.mangopay.checkout.example.model.response

import com.mangopay.android.core.model.objectclass.Currency
import com.mangopay.android.core.model.request.FeesCreditedDebitedFunds
import com.mangopay.android.core.model.request.FeesRequest
import com.mangopay.android.core.model.request.FundsRequest
import com.mangopay.android.core.model.request.LineItemRequest
import com.mangopay.android.core.model.request.PaypalPayment
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

fun PaypalPaymentResponse.toPaypalPayment(): PaypalPayment {
    return PaypalPayment.Builder()
        .id(this.id)
        .tag(this.tag)
        .creationDate(this.creationDate)
        .authorId(this.authorId)
        .debitedFunds(
            FundsRequest.Builder()
                .amount(this.debitedFunds?.amount)
                .currency(Currency.get(this.debitedFunds?.currency))
                .build()
        )
        .creditedFunds(
            FundsRequest.Builder()
                .amount(this.creditedFunds.amount)
                .currency(Currency.get(this.creditedFunds.currency))
                .build()
        )
        .fees(
            FeesRequest.Builder()
            .amount(this.fees?.amount)
            .currency(Currency.get(this.fees?.currency))
            .build()
        )
        .status(this.status)
        .resultCode(this.resultCode)
        .resultMessage(this.resultMessage)
        .executionDate(this.executionDate)
        .type(this.type)
        .shippingPreference(this.shippingPreference)
        .nature(this.nature)
        .creditedUserId(this.creditedUserId)
        .creditedWalletId(this.creditedWalletId)
        .paymentType(this.paymentType)
        .executionType(this.executionType)
        .redirectURL(this.redirectURL)
        .returnURL(this.returnURL)
        .statementDescriptor(this.statementDescriptor)
        .culture(this.culture)
        .shippingPreference(this.shippingPreference)
        .paypalBuyerAccountEmail(this.paypalBuyerAccountEmail)
        .reference(this.reference)
        .trackings(this.trackings)
        .lineItems(this.lineItems.map { it.toLineItemRequest() })
        .build()
}

fun LineItem.toLineItemRequest() = LineItemRequest.Builder()
    .description(this.description)
    .taxAmount(this.taxAmount)
    .name(this.name)
    .quantity(this.quantity)
    .unitAmount(this.unitAmount)
    .build()
