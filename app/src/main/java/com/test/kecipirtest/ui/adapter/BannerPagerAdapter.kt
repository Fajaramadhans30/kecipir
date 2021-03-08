package com.test.kecipirtest.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.test.kecipirtest.R
import com.test.kecipirtest.data.model.banner.Banner
import com.test.kecipirtest.util.showImageUrl
import kotlinx.android.synthetic.main.banner_layout_item.view.*

class BannerPagerAdapter(
    private val context: Context,
    private val datas: List<Banner>,
    private val onBannerClicked: ((product: Banner) -> Unit)? = null
) : PagerAdapter() {
    val TAG: String = "BannerPagerAdapter"
    private lateinit var newUrlImage :String

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layout =
            LayoutInflater.from(context).inflate(R.layout.banner_layout_item, container, false)

        with(layout) {
            Log.d(TAG, "IMAGEEE: " + datas[0].image)
            if (datas[position].image.equals("https://cdn.kecipir.com/images_news/thumb/x_f2d53c0576137015f1b517487b6f6cd7.png.webpthumb/x_c7b9123e71300ef22e30865b8e1ae043.png.webp")) {
                newUrlImage = "https://cdn.kecipir.com/images_news/thumb/x_c7b9123e71300ef22e30865b8e1ae043.png.webp"
            } else if (datas[position].image.equals("https://cdn.kecipir.com/images_news/thumb/x_f2d53c0576137015f1b517487b6f6cd7.png.webpthumb/x_7cb2d30622c1c9cfeafbf92b9ad03c24.png.webp")) {
                newUrlImage = "https://cdn.kecipir.com/images_news/thumb/x_7cb2d30622c1c9cfeafbf92b9ad03c24.png.webp"
            } else if (datas[position].image.equals("https://cdn.kecipir.com/images_news/thumb/x_f2d53c0576137015f1b517487b6f6cd7.png.webpthumb/x_4d40f74031468cb059a97b9262185619.png.webp")) {
                newUrlImage = "https://cdn.kecipir.com/images_news/thumb/x_4d40f74031468cb059a97b9262185619.png.webp"
            } else if (datas[position].image.equals("https://cdn.kecipir.com/images_news/thumb/x_f2d53c0576137015f1b517487b6f6cd7.png.webpthumb/x_de02161ea9321cfe5cca613ac5dd3a70.png.webp")) {
                newUrlImage = "https://cdn.kecipir.com/images_news/thumb/x_de02161ea9321cfe5cca613ac5dd3a70.png.webp"
            } else {
                newUrlImage = "https://cdn.kecipir.com/images_news/thumb/x_f2d53c0576137015f1b517487b6f6cd7.png.webp"
            }
            ivBanner.showImageUrl(context, newUrlImage)

            setOnClickListener {
                onBannerClicked?.invoke(datas[position])
            }
        }

        container.addView(layout)
        return layout
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        Log.d(TAG, "getCount: " + datas.size)
        return datas.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

}