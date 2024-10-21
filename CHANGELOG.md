
# 🔀 &nbsp; Changelog

## 1.0.8 (2024-10-21)

### Nethone Profiling Integration fixes
* Nethone profiling fixes, now require the following to be added to the `settings.gradle.kts`.
```
    maven {
        url = uri("https://nethone.jfrog.io/artifactory/nethonesdk-android-mangopay/")
    }
```
* Improved system logging.
* `CB` and `AMEX` logo fixes.

## 1.0.6 (2024-06-21)

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

