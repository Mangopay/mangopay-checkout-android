package com.mangopay.checkout.example.shopping

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.mangopay.android.checkout.core.MangopayCheckoutSdk
import com.mangopay.android.core.Mangopay
import com.mangopay.android.core.card.Card
import com.mangopay.android.core.card.CardScheme
import com.mangopay.android.core.exception.MangopayException
import com.mangopay.android.core.model.CardRegistration
import com.mangopay.android.core.model.PaymentMethodOptions
import com.mangopay.android.core.model.objectclass.CardType
import com.mangopay.android.core.model.objectclass.Currency
import com.mangopay.android.core.model.paymentmethods.GooglepayObject
import com.mangopay.android.core.model.paymentmethods.PaymentMethod
import com.mangopay.android.core.model.request.Amount
import com.mangopay.android.core.model.request.CardOptions
import com.mangopay.android.core.model.request.CreateCardRequest
import com.mangopay.android.core.model.request.Payment
import com.mangopay.android.core.model.request.PaypalOptions
import com.mangopay.android.core.model.response.CardRegistrationResponse
import com.mangopay.android.core.util.ResultCode
import com.mangopay.android.core.util.isValidUrl
import com.mangopay.checkout.example.R
import com.mangopay.checkout.example.client.KtorAPI
import com.mangopay.checkout.example.databinding.ActivityShoppingMarketBinding
import com.mangopay.checkout.example.model.response.PayInPaymentResponse
import com.mangopay.checkout.example.model.response.PaypalPaymentResponse
import com.mangopay.checkout.example.model.DemoProductObject
import com.mangopay.checkout.example.model.TestPaymentData
import com.mangopay.checkout.example.model.createPayinRequest
import com.mangopay.checkout.example.model.request.createPaypalRequest
import com.mangopay.checkout.example.model.googlePayConfigTestData
import com.mangopay.checkout.example.model.response.toCardPayment
import com.mangopay.checkout.example.model.toCardResult
import com.mangopay.checkout.example.model.response.toPaypalPayment
import com.mangopay.checkout.example.model.toRestRequest
import com.mangopay.checkout.example.shopping.adapter.ProductsAdapter
import kotlinx.coroutines.async
import org.koin.android.ext.android.inject

class ShoppingMarketActivity : AppCompatActivity() {

    private val TAG = "ShoppingMarketActivity"
    private lateinit var paymentSuccessBottomSheetDialog: BottomSheetDialog
    private lateinit var binding: ActivityShoppingMarketBinding
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var resultTextView: TextView
    private var demoResult = "Nothing yet!"
    private val ktorAPI: KtorAPI by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSuccessBottomDialog()

        productsAdapter = ProductsAdapter(currency = Currency.EUR) { product ->
                val cardOptions = CardOptions.Builder()
                    .supportedCardSchemes(
                        listOf(
                            CardScheme.Maestro,
                            CardScheme.MasterCard,
                            CardScheme.Visa,
                            )
                    ).build()

                val paymentMethodOptions = PaymentMethodOptions.Builder()
                    .cardOptions(cardOptions)
                    .googlePayOptions(
                        googlePayConfigTestData()
                    )
                    .paypalOptions(
                        PaypalOptions.Builder()
                            .build()
                    )
                    .amount(
                        Amount.Builder()
                            .value(200.00)
                            .currency(Currency.EUR)
                            .build()
                    )
                    .build()

                MangopayCheckoutSdk.create(
                    mContext = this,
                    paymentMethodOptions = paymentMethodOptions,
                    listener = paymentSheetCallbacks()
                )
        }

        binding.recyclerView.apply {
            adapter = productsAdapter
            setHasFixedSize(true)
        }
        fetchProducts()
    }


    private fun paymentSheetCallbacks() = object : Mangopay.PaymentSheetResultListener {
        override fun onTokenizationCompleted(paymentMethod: PaymentMethod?, profilerAttemptReference: String?) {
            var resultStatus = "PaymentSheetResult.TokenizationCompleted()".addLines()

            when (paymentMethod) {
                is PaymentMethod.CARD -> {
                    resultStatus += "PaymentMethod.CARD()".addLines()
                    resultStatus += profilerAttemptReference.toString().addLines()
                    resultStatus += paymentMethod.result.toString().addLines()
                    TestPaymentData.cardId = paymentMethod.result?.cardId.orEmpty()
                    Log.v(TAG, resultStatus)
                }

                is PaymentMethod.GOOGLE_PAY -> {
                    MangopayCheckoutSdk.tearDown()
                    resultStatus += "PaymentMethod.GPAY()".addLines()
                    resultStatus += profilerAttemptReference.toString()
                        .addLines()
                    resultStatus += paymentMethod.token?.addLines()
                    paymentSuccessBottomSheetDialog.show()
                }

                else -> {
                    MangopayCheckoutSdk.tearDown()
                    resultStatus += "else()".addLines()
                }
            }
            demoResult = resultStatus
            resultTextView.text = resultStatus
            Log.v(TAG, resultStatus)
        }

        override fun onPaymentCompleted(id: String?, resultCode: ResultCode) {
            var resultStatus =
                "PaymentSheetResult.TokenizationCompleted()".addLines()
            resultStatus += id?.addLines()
            resultStatus += resultCode.status.addLines()
            demoResult = resultStatus
            resultTextView.text = resultStatus
            MangopayCheckoutSdk.tearDown()
            paymentSuccessBottomSheetDialog.show()
            Log.v(TAG, resultStatus)
        }

        override fun onPaymentMethodSelected(paymentMethod: PaymentMethod) {
            Log.v(TAG, paymentMethod.toString())
        }

        override suspend fun onCreatePayment(paymentMethod: PaymentMethod,
                                             profilerAttemptReference: String?): Payment? {
            TestPaymentData.profilingAttemptReference = profilerAttemptReference.orEmpty()
            return when(paymentMethod){
                is PaymentMethod.PAYPAL -> {
                    fetchPaypal()?.toPaypalPayment()
                }

                is PaymentMethod.CARD -> {
                    val cardPaymentResult = fetchPayin()?.toCardPayment()
                    if (cardPaymentResult != null && cardPaymentResult.secureModeRedirectURL?.isValidUrl() == true) {
                        return cardPaymentResult
                    } else null
                }

                is PaymentMethod.GOOGLE_PAY -> TODO()
                is GooglepayObject -> TODO()
            }
        }

        override suspend fun onCreateCardRegistration(card: Card): CardRegistration {
            val createCardRequest = CreateCardRequest.Builder()
                .userId(TestPaymentData.mgpUserId)
                .cardType(CardType.CB_VISA_MASTERCARD)
                .currency(Currency.EUR)
                .build()

            val cardRegistrationResult = initCreateCardRegistrations(createCardRequest)?.toCardResult()
            return CardRegistration.Builder()
                .id(cardRegistrationResult?.id.orEmpty())
                .userId(cardRegistrationResult?.userId ?: TestPaymentData.mgpUserId)
                .accessKey(cardRegistrationResult?.accessKey.orEmpty())
                .preRegistrationData(cardRegistrationResult?.preRegistrationData.orEmpty())
                .cardRegistrationURL(cardRegistrationResult?.cardRegistrationURL.orEmpty())
                .build()
        }

        override fun onError(exception: MangopayException?) {
            var resultStatus = "PaymentSheetResult.Error()".addLines()
            resultStatus += exception.toString().addLines()
            Log.v(TAG, resultStatus)
        }

        override fun onCancel() {
            val resultStatus = "PaymentSheetResult.PaymentSheetCancelled()".addLines()
            Log.v(TAG, resultStatus)
        }
    }

    private suspend fun fetchPaypal(): PaypalPaymentResponse? {
        return try {
            lifecycleScope.async {
                ktorAPI.createPaypal(
                    passphrase = TestPaymentData.mgpPassphrase,
                    clientId = TestPaymentData.mgpClientId,
                    request = createPaypalRequest()
                )
            }.await()
        }catch (e:Exception){
            Log.v(TAG, e.message.orEmpty())
            return null
        }
    }

    private suspend fun fetchPayin(): PayInPaymentResponse? {
        return try {
            lifecycleScope.async {
                ktorAPI.createPayin(
                    passphrase = TestPaymentData.mgpPassphrase,
                    clientId =  TestPaymentData.mgpClientId,
                    request = createPayinRequest()
                )
            }.await()
        }catch (e:Exception){
            Log.v(TAG, e.message.orEmpty())
            return null
        }
    }

    private fun fetchProducts() = productsAdapter.submitList(DemoProductObject.getFakeProducts())

    private fun setupSuccessBottomDialog() {
        paymentSuccessBottomSheetDialog = BottomSheetDialog(this)
        paymentSuccessBottomSheetDialog.setCanceledOnTouchOutside(false)
        paymentSuccessBottomSheetDialog.setCancelable(false)
        val sheetView =
            this.layoutInflater.inflate(R.layout.modal_payment_successful, null)
        sheetView.findViewById<MaterialButton>(R.id.btn_cont_shopping).setOnClickListener {
            paymentSuccessBottomSheetDialog.dismiss()
            val intent = Intent(this, ShoppingMarketActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        resultTextView = sheetView.findViewById(R.id.txt_result_preview)
        sheetView.findViewById<ImageView>(R.id.img_copy_result).setOnClickListener {
            val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
            clipboard?.setPrimaryClip(ClipData.newPlainText("checkout_demo_result", demoResult))
            Toast.makeText(this, "Result Copied!", Toast.LENGTH_SHORT).show()
        }
        paymentSuccessBottomSheetDialog.setContentView(sheetView)
    }

    private suspend fun initCreateCardRegistrations(request: CreateCardRequest): CardRegistrationResponse? {
        return try {
            lifecycleScope.async {
                ktorAPI.createCardRegistrations(
                    passphrase = TestPaymentData.mgpPassphrase,
                    clientId = TestPaymentData.mgpClientId,
                    request = request.toRestRequest()
                )
            }.await()
        }catch (e:Exception){
            Log.v(TAG, e.message.orEmpty())
            return null
        }
    }

    private fun String.addLines(): String {
        return this+"\n\n"
    }
}