package com.test.kecipirtest.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.kecipirtest.R
import com.test.kecipirtest.data.model.category.Category
import com.test.kecipirtest.util.showImageUrl
import kotlinx.android.synthetic.main.category_layout_item.view.*

class CategoryAdapter(private val context: Context,
private val datas: List<Category>,
private val onBannerClicked: ((category: Category) -> Unit)? = null
) :RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.category_layout_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Category) {
            with(itemView) {
                ivCategory.showImageUrl(context, data.link)
                tvCategory.text = data.category
                setOnClickListener {
                    onBannerClicked?.invoke(data)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}