package com.test.kecipirtest.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.whatif.whatIfNotNull
import com.skydoves.whatif.whatIfNotNullOrEmpty
import com.test.kecipirtest.R
import com.test.kecipirtest.data.model.product.Product
import com.test.kecipirtest.util.showImageUrl
import kotlinx.android.synthetic.main.product_layout_item.view.*

class ProductAdapter(
    private val context: Context,
    private val datas: List<Product>,
    private val onProductClicked:((data:Product)->Unit)? = null,
    private val type: String = ALL_PRODUCT
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    companion object{
        const val ALL_PRODUCT = "all_product"
        const val FEW_PRODUCT = "few_product"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(context).inflate(R.layout.product_layout_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        datas.whatIfNotNullOrEmpty {
            holder.bind(datas[position])
        }
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Product) {
            with(itemView) {
                val pricesPromo = String.format("%,d", data.promoPrice.toLong())
                val prices = String.format("%,d", data.sellPrice.toLong())

                ivProductPhoto.showImageUrl(context, data.photo)
                tvFarmName.text = data.farmer
                tvProductName.text = data.title
                tvGrade.text = data.grade
                tvGrade.setTextColor(Color.parseColor(data.gradeColor))
                tvWeight.text = data.unit

                data.whatIfNotNull {
                    if (it.discount == "0%") {
                        lnDiscount.visibility = View.GONE
                        tvProductPriceDiscount.visibility = View.GONE
                        tvPrice.visibility = View.VISIBLE
                        tvPrice.text = "Rp $prices"
                    } else {
                        lnDiscount.visibility = View.VISIBLE
                        tvPrice.visibility = View.VISIBLE
                        tvProductPriceDiscount.visibility = View.VISIBLE
                        tvProductPriceDiscount.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        tvProductPriceDiscount.text = "Rp $pricesPromo"
                        tvPrice.text = "Rp $prices"
                    }

                    if (it.stock < 10) {
                        lnStock.setBackgroundResource(R.drawable.textbox_style_no_bg_orange)
                        tvStock.setTextColor(context.resources.getColor(R.color.colorAccentOrange))
                    } else {
                        lnStock.setBackgroundResource(R.drawable.textbox_style_no_bg_green)
                        tvStock.setTextColor(context.resources.getColor(R.color.colorAccent))
                    }

                    if (it.grade == "Perlakuan Organik ") {
                        lnGrade.setBackgroundResource(R.drawable.textbox_style_blueopacity)
                    } else if (data.grade == "Organik Bersertifikat") {
                        lnGrade.setBackgroundResource(R.drawable.textbox_style_greenopacity)
                    } else {
                        lnGrade.setBackgroundResource(R.drawable.textbox_style_orangeopacity)
                    }
                }

                tvStock.text = "Stock" + " " + data.stock.toString()
                setOnClickListener {
                    onProductClicked?.invoke(data)
                }
            }
        }
    }

    override fun getItemCount(): Int = if(type == ALL_PRODUCT) datas.size else 10


}