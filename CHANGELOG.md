
# 🔀 &nbsp; Changelog

## 1.1.0 (2024-06-21)

### Features & Improvements
* Added card cvv validations with card type
* Added`cardHolderName` to vault sdk
* Update privacy policy ui design
* Minor bug fixes for improvement

## 1.0.3 (2024-05-07)

### Improvement to Vault sdk
* Changed dependency name from `vault-sdk` to `vault`, the complete new dependency is now `implementation("com.mangopay.android:vault-sdk:<latest-version>")`.
* Changed Initialization method from `MangopayVaultSdk.initialize()` to `MangopaySdk.initialize()`.
* Added `context` as required parameter and optional `tenantId` as part of the initialization parameters.

