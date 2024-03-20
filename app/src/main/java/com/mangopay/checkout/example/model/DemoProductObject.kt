package com.mangopay.checkout.example.model

import android.os.Parcelable
import com.mangopay.checkout.example.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class DemoProductObject(
    val id: Int,
    val productName: String,
    val productPrice: Int,
    val productImage: Int
) : Parcelable {

    companion object {
        private val shoes = listOf(R.drawable.shoe1, R.drawable.shoe2)
        fun getFakeProducts() = List(7) {
            val initIndex = it+1
            DemoProductObject(it, "SneakShu ${initIndex}00", 10 * initIndex, shoes.random())
        }
    }
}

