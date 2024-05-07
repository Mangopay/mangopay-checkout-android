package com.mangopay.checkout.example.model.response

import com.mangopay.android.core.model.objectclass.Currency
import com.mangopay.android.core.model.request.AddressRequest
import com.mangopay.android.core.model.request.BillingRequest
import com.mangopay.android.core.model.request.BillingShipping
import com.mangopay.android.core.model.request.BrowserInfo
import com.mangopay.android.core.model.request.BrowserInfoRequest
import com.mangopay.android.core.model.request.CardPayment
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

fun PayInPaymentResponse.toCardPayment(): CardPayment {
    return CardPayment.Builder()
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
                .amount(this.creditedFunds?.amount)
                .currency(Currency.get(this.creditedFunds?.currency))
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
        .nature(this.nature)
        .creditedUserId(this.creditedUserId)
        .creditedWalletId(this.creditedWalletId)
        .debitedWalletId(this.debitedWalletId)
        .paymentType(this.paymentType)
        .executionType(this.executionType)
        .statementDescriptor(this.statementDescriptor)
        .culture(this.culture)
        .secureMode(this.secureMode)
        .cardId(this.cardId)
        .secureModeReturnURL(this.secureModeReturnURL)
        .secureModeRedirectURL(this.secureModeRedirectURL)
        .secureModeNeeded(this.secureModeNeeded)
        .securityInfoRequest(
            SecurityInfoRequest.Builder()
                .aVSResult(this.securityInfo?.aVSResult.orEmpty())
                .build()
        )
        .browserInfo(
            BrowserInfoRequest.Builder()
                .acceptHeader(this.browserInfo?.acceptHeader)
                .colorDepth(this.browserInfo?.colorDepth)
                .javaEnabled(this.browserInfo?.javaEnabled)
                .javascriptEnabled(this.browserInfo?.javascriptEnabled)
                .language(this.browserInfo?.language)
                .screenHeight(this.browserInfo?.screenHeight)
                .screenWidth(this.browserInfo?.screenWidth)
                .timeZoneOffset(this.browserInfo?.timeZoneOffset)
                .userAgent(this.browserInfo?.userAgent)
                .build()
        )
        .ipAddress(this.ipAddress)
        .billingRequest(
            BillingRequest.Builder()
                .firstName(this.billing?.firstName)
                .lastName(this.billing?.lastName)
                .address(
                    AddressRequest.Builder()
                        .addressLine1(this.billing?.address?.addressLine1)
                        .addressLine2(this.billing?.address?.addressLine2)
                        .city(this.billing?.address?.city)
                        .country(this.billing?.address?.country)
                        .postalCode(this.billing?.address?.postalCode)
                        .region(this.billing?.address?.region)
                        .build()
                ).build()
        )
        .shippingRequest(
            ShippingRequest.Builder()
                .firstName(this.shipping?.firstName)
                .lastName(this.shipping?.lastName)
                .address(
                    AddressRequest.Builder()
                        .addressLine1(this.shipping?.address?.addressLine1)
                        .addressLine2(this.shipping?.address?.addressLine2)
                        .city(this.shipping?.address?.city)
                        .country(this.shipping?.address?.country)
                        .postalCode(this.shipping?.address?.postalCode)
                        .region(this.shipping?.address?.region)
                        .build()
                ).build()
        )
        .requested3DSVersion(this.requested3DSVersion)
        .applied3DSVersion(this.applied3DSVersion)
        .recurringPayinRegistrationId(this.recurringPayInRegistrationId)
        .build()
}
