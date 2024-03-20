package com.mangopay.checkout.example.model

import com.mangopay.android.core.model.objectclass.CardType
import com.mangopay.android.core.model.objectclass.Currency
import com.mangopay.android.core.model.request.CreateCardRegRestRequest
import com.mangopay.android.core.model.request.CreateCardRequest
import com.mangopay.android.core.model.response.CardRegistrationResponse
import com.mangopay.android.core.model.result.CardResult

fun CreateCardRequest.toRestRequest() = CreateCardRegRestRequest(
    tag = this.tag,
    userId = this.userId,
    cardType = this.cardType.type,
    currency = this.currency.currency
)

fun CardRegistrationResponse.toCardResult() = CardResult(
    id = this.id,
    tag = this.tag,
    creationDate = this.creationDate,
    userId = this.userId,
    accessKey = this.accessKey,
    preRegistrationData = this.preRegistrationData,
    registrationData = this.registrationData,
    cardId = this.cardId,
    cardType = CardType.get(this.cardType),
    cardRegistrationURL = this.cardRegistrationURL,
    resultCode = this.resultCode,
    resultMessage = this.resultMessage,
    currency = Currency.get(this.currency),
    status = this.status
)