package com.mangopay.checkout.example.model.response

import com.mangopay.android.core.card.Card
import com.mangopay.android.core.model.objectclass.Currency
import com.mangopay.android.core.model.objectclass.Payment
import com.mangopay.android.core.model.request.AddressRequest
import com.mangopay.android.core.model.request.BillingRequest
import com.mangopay.android.core.model.request.BillingShipping
import com.mangopay.android.core.model.request.BrowserInfo
import com.mangopay.android.core.model.request.BrowserInfoRequest
import com.mangopay.android.core.model.request.CardOptions
import com.mangopay.android.core.model.request.FeesCreditedDebitedFunds
import com.mangopay.android.core.model.request.FeesRequest
import com.mangopay.android.core.model.request.FundsRequest
import com.mangopay.android.core.model.request.SecurityInfoRequest
import com.mangopay.android.core.model.request.ShippingRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PayInPaymentResponse(
    @SerialName("Applied3DSVersion")
    val applied3DSVersion: String?,

    @SerialName("AuthorId")
    val authorId: String,

    @SerialName("Billing")
    val billing: BillingShipping?,

    @SerialName("BrowserInfo")
    val browserInfo: BrowserInfo?,

    @SerialName("CardId")
    val cardId: String,

    @SerialName("CreationDate")
    val creationDate: Int,

    @SerialName("CreditedFunds")
    val creditedFunds: FeesCreditedDebitedFunds?,

    @SerialName("CreditedUserId")
    val creditedUserId: String?,

    @SerialName("CreditedWalletId")
    val creditedWalletId: String?,

    @SerialName("Culture")
    val culture: String,

    @SerialName("DebitedFunds")
    val debitedFunds: FeesCreditedDebitedFunds?,

    @SerialName("DebitedWalletId")
    val debitedWalletId: String?,

    @SerialName("ExecutionDate")
    val executionDate: Int?,

    @SerialName("ExecutionType")
    val executionType: String,

    @SerialName("Fees")
    val fees: FeesCreditedDebitedFunds?,

    @SerialName("Id")
    val id: String,

    @SerialName("IpAddress")
    val ipAddress: String,

    @SerialName("Nature")
    val nature: String,

    @SerialName("PaymentType")
    val paymentType: String,

    @SerialName("RecurringPayinRegistrationId")
    val recurringPayInRegistrationId: String?,

    @SerialName("Requested3DSVersion")
    val requested3DSVersion: String?,

    @SerialName("ResultCode")
    val resultCode: String?,

    @SerialName("ResultMessage")
    val resultMessage: String?,

    @SerialName("SecureMode")
    val secureMode: String,

    @SerialName("SecureModeNeeded")
    val secureModeNeeded: Boolean,

    @SerialName("SecureModeRedirectURL")
    val secureModeRedirectURL: String?,

    @SerialName("SecureModeReturnURL")
    val secureModeReturnURL: String?,

    @SerialName("SecurityInfo")
    val securityInfo: SecurityInfo?,

    @SerialName("Shipping")
    val shipping: BillingShipping?,

    @SerialName("StatementDescriptor")
    val statementDescriptor: String,

    @SerialName("Status")
    val status: String,

    @SerialName("Tag")
    val tag: String,

    @SerialName("Type")
    val type: String
)

@Serializable
data class SecurityInfo(
    @SerialName("AVSResult")
    val aVSResult: String?
)

fun PayInPaymentResponse.toPaymentImpl() = PaymentImpl(
    id = this.id,
    status = this.status,
    returnURL = this.secureModeReturnURL,
    redirectURL = this.secureModeRedirectURL
)

