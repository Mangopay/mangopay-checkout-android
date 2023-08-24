# Vault SDK Example

<h4 align="center">
 ℹ️ Code Sample coming soon.
</h4>

<br />

## Introduction

The Mangopay Vault SDK allows you to securely tokenize an end user’s payment card for use in your application. A tokenized card is a virtual and secure version of the card that can be used for payment.

It is very highly recommended that you use the Mangopay Vault SDK, rather than integrating the API endpoints directly. By doing so, you:

- Avoid sensitive card details transiting your system
- Benefit from PCI-DSS compliance
- Receive ongoing support and updates

<aside>
<img src="https://www.notion.so/icons/square-alternate_lightgray.svg" alt="https://www.notion.so/icons/square-alternate_lightgray.svg" width="40px" /> **Prerequisites**

To use the Mangopay Vault SDK, you’ll need:

- A Mangopay `ClientId` and API key
- A User to register the card for (see [Testing - Payment methods](https://preview-documentation.swarm.preprod.mangopay.com/docs/dev-tools/testing/payment-methods) for test cards)
- Android `minSdk = 21`
</aside>

## Installation

Add the following dependency to build.gradle (module): 

```kotlin
implementation("com.mangopay.android:vault-sdk:<latest-version>")
```

## Initializing the SDK

Initialize the SDK with your `ClientId` and select your environment (Sandbox or Production). 

```kotlin
MangopayVaultSdk.initialize(clientId = "your-mangopay-client-id", environment = Environment.SANDBOX)
```

### Optional initialization parameters

| Argument | Type | Description |
| --- | --- | --- |
| logLevel | LogLevel | Use this to specify HTTP request and response log. We recommend LogLevel.None for Production build.

Default value: LogLevel.None
Allowed values: LogLevel.None , LogLevel.Basic |
| environment | Environment | Expected backend environment.

Default value: Environment.SANDBOX
Allowed values:Environment.SANDBOX, Environment.PRODUCTION |

## Creating the Card Registration

In your backend, create the Card Registration via the Mangopay API, using the `Id` of the end user as the `UserId` .

You must also define the currency and type of the card at this stage.

<aside>
<img src="https://www.notion.so/icons/square-alternate_gray.svg" alt="https://www.notion.so/icons/square-alternate_gray.svg" width="40px" /> **POST** /v2.01/`ClientId`/cardregistrations

```json
{
    "Tag": "Created with the Mangopay Vault SDK",
    "UserId": "142036728",
    "CardType": "CB_VISA_MASTERCARD",
    "Currency": "EUR"
}
```

[See parameter details →](https://preview-documentation.swarm.preprod.mangopay.com/docs/endpoints/direct-card-payins#create-card-registration)

</aside>

### API response

```json
{
    "Id": "193020188",
    "Tag": null,
    "CreationDate": 1686147148,
    "UserId": "193020185",
    "AccessKey": "1X0m87dmM2LiwFgxPLBJ",
    "PreregistrationData": "XBDYiG8w9PrylPS01KmupZunmK2QRHKIC-yUF6il3aIpAnKba1TGkR9VJe5lHjHt2ddFLVXdicolcUIkv_kKEA",
    "RegistrationData": null,
    "CardId": null,
    "CardType": "CB_VISA_MASTERCARD",
    "CardRegistrationURL": "https://homologation-webpayment.payline.com/webpayment/getToken",
    "ResultCode": null,
    "ResultMessage": null,
    "Currency": "EUR",
    "Status": "CREATED"
}
```

The data obtained in the response will be used in the `CardRegistration` defined below.

## Providing data for tokenization

The SDK requires the following information to tokenize the card:

- The Card Registration data (`CardRegistration`) previously returned by the Mangopay API
- The end user’s card details (`Card`) entered in the app (see [Testing - Payment methods](https://preview-documentation.swarm.preprod.mangopay.com/docs/dev-tools/testing/payment-methods) for test cards)

### CardRegistration

| Property | Type | Description |
| --- | --- | --- |
| Id | string | The unique identifier of the Card Registration object. |
| AccessKey | string | The secure value used when tokenizing the card. |
| PreregistrationData | string | A specific value passed to the CardRegistrationURL. |
| CardRegistrationURL                                                                     | string | The URL to which the card details are sent to be tokenized. |

```kotlin
val cardRegistration = CardRegistration.Builder()
            .id(Id)
            .accessKey(AccessKey)
            .preRegistrationData(PreregistrationData)
            .cardRegistrationURL(CardRegistrationURL)
            .build()
```

### Card

| Property | Type | Description |
| --- | --- | --- |
| number | string | The card number to be tokenized, without any separators. |
| expirationDate | string (Format: “MMYY”) | The expiration date of the card. |
| cvv | string | The card verification code (on the back of the card, usually 3 digits). |

```kotlin
val card = Card.Builder()
    .number("4970107111111119")
    .expirationDate("1224")
    .cvv("123")
    .build()
```

## Tokenizing the card

You can now tokenize the card with the card data obtained previously using the frontend SDK.

The SDK automatically updates the Card Registration object to provide you with a `CardId` that can be used for payments. 

### TokenizeCard

```kotlin
// Define the callback to receive tokenization result
private fun tokenizeCallbacks() = object: Mangopay.TokenizeCardResultCallback{
        override fun success(result: CardRegistration?) {
            // You can use result.cardId to process payments from your backend
        }

        override fun error(exception: MangopayException) {
           // An error has occured
        }
}

// Invoke tokenizeCard method
MangopayVaultSdk.tokenizeCard(card, cardRegistration, this, tokenizeCallbacks())
```

## Managing cards

You can use the following endpoints to manage cards: 

- [View a Card](https://mangopay.com/docs/endpoints/direct-card-payins#view-card) provides key information about the card, including its `Fingerprint` which can be used as an [anti-fraud tool](https://mangopay.com/docs/concepts/payments/payment-methods/card/anti-fraud-tools#card-fingerprint)
- [Deactivate a Card](https://mangopay.com/docs/endpoints/direct-card-payins#deactivate-card) allows you to irreversibly set the card as inactive

## Making card pay-ins

You can use a registered card (`CardId`) for pay-ins with the following objects:

- [The Direct Card PayIn object](https://mangopay.com/docs/endpoints/direct-card-payins#direct-card-payin-object), for one-shot card payments
- [The Recurring PayIn Registration object](https://preview-documentation.swarm.preprod.mangopay.com/docs/endpoints/recurring-card-payins#recurring-payin-registration-object), for recurring card payments
- [The Preauthorization object](https://preview-documentation.swarm.preprod.mangopay.com/docs/endpoints/preauthorizations#preauthorization-object), for 7-day preauthorized card payments
- [The Deposit Preauthorization object](https://preview-documentation.swarm.preprod.mangopay.com/docs/endpoints/preauthorizations#deposit-preauthorization-object), for 30-day preauthorized card payments

## Related resources

- [Testing - Payment methods](https://mangopay.com/docs/dev-tools/testing/payment-methods)
- [All supported payment methods](https://mangopay.com/docs/concepts/payments/payment-methods/all)