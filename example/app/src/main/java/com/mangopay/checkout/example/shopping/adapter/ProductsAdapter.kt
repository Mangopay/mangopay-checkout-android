package com.mangopay.checkout.example.shopping.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mangopay.android.core.model.objectclass.Currency
import com.mangopay.checkout.example.databinding.ItemProductLayoutBinding
import com.mangopay.checkout.example.model.DemoProductObject


class ProductsAdapter constructor(private val currency: Currency = Currency.EUR, val onBuyNowClicked: (DemoProductObject) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<DemoProductObject, ProductsAdapter.ProductsViewHolder>(ProductListCallback()) {

    companion object {
        class ProductListCallback: DiffUtil.ItemCallback<DemoProductObject>() {
            override fun areItemsTheSame(oldItem: DemoProductObject, newItem: DemoProductObject): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DemoProductObject, newItem: DemoProductObject): Boolean {
                return  oldItem == newItem
            }
        }
    }

    inner class ProductsViewHolder(private val binding: ItemProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(demoProductObject: DemoProductObject) {
            with(binding) {
                productName.text = demoProductObject.productName
                productPrice.text = "${currency.currency} ${demoProductObject.productPrice}"
                productImage.setImageDrawable(ContextCompat.getDrawable(binding.root.context, demoProductObject.productImage))
                btnBuyNow.setOnClickListener {
                    onBuyNowClicked(demoProductObject)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return from(parent)
    }

    private fun from(parent: ViewGroup): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductLayoutBinding.inflate(layoutInflater, parent, false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

