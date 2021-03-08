package com.test.kecipirtest.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.kecipirtest.R
import com.test.kecipirtest.data.realm.ProductRealm
import com.test.kecipirtest.util.showImageUrl
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_cart_item_layout.view.*

class CartAdapter(
    private val context: Context,
    private val items: RealmResults<ProductRealm>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(context).inflate(R.layout.activity_cart_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        holder.bind(items[position])

    }

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(data: ProductRealm?) {
            with(itemView) {
                val prices = String.format("%,d", data?.sell_price?.toLong())

                ivProductPhotoBuy.showImageUrl(context, data?.photo.toString())
                tvProductNameBuy.text = data?.title
                tvProductPriceBuy.text = prices
                tvFarmName.text = data?.farmer
                tvWeight.text = data?.unit
                tvProductStock.text = "Stock " +data?.stock
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}