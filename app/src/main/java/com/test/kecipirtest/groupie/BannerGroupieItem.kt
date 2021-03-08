package com.test.kecipirtest.groupie

import android.content.Context
import android.os.Handler
import com.kennyc.view.MultiStateView
import com.test.kecipirtest.R
import com.test.kecipirtest.data.model.banner.Banner
import com.test.kecipirtest.ui.adapter.BannerPagerAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.banner_layout.*
import java.util.*


class BannerGroupieItem(
    private val context: Context,
    private var datas: List<Banner>,
    private val onBannerClicked: ((product: Banner) -> Unit)? = null
) : Item() {

    var viewState = MultiStateView.ViewState.LOADING
    private var currentPage = 0

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder) {
            msvBanner.viewState = viewState
            val bannerAdapter = BannerPagerAdapter(context, datas, onBannerClicked)
            viewpagerBanner.adapter = bannerAdapter
//            bannerIndicator.setViewPager(viewpagerBanner)
            indicator.setViewPager(viewpagerBanner)

            bannerAdapter.registerDataSetObserver(indicator.dataSetObserver)


            val handler = Handler()
            val Update = Runnable {
                if (currentPage === datas.size) {
                    currentPage = 0
                }
                viewpagerBanner.setCurrentItem(currentPage++, true)
            }
            val swipeTimer = Timer()
            swipeTimer.schedule(object : TimerTask() {
                override fun run() {
                    handler.post(Update)
                }
            }, 5000, 5000)
        }
    }


    override fun getLayout(): Int = R.layout.banner_layout

    fun add(datas: List<Banner>) {
        this.datas = datas
    }

//    private fun setupIndicator(
//        bannerAdapter: BannerPagerAdapter,
//        sliderDots: LinearLayout
//    ) {
//        val indicator =
//            arrayOfNulls<ImageView>(bannerAdapter.count)
//        val layoutParams = LinearLayout.LayoutParams(
//            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        layoutParams.setMargins(4, 0, 4, 0)
//        for (i in indicator.indices) {
//            indicator[i] =
//                ImageView(context)
//            indicator[i]!!.setImageDrawable(
//                ContextCompat.getDrawable(
//                    context,
//                    R.drawable.non_active_dots
//                )
//            )
//            indicator[i]!!.layoutParams = layoutParams
//            sliderDots.addView(indicator[i])
//        }
//    }
//
//    private fun setupCurrentIndicator(index: Int, sliderDots: LinearLayout) {
//        val itemcildcount: Int = sliderDots.getChildCount()
//        for (i in 0 until itemcildcount) {
//            val imageView =
//                sliderDots.getChildAt(i) as ImageView
//            if (i == index) {
//                imageView.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        context,
//                        R.drawable.active_dots
//                    )
//                )
//            } else {
//                imageView.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        context,
//                        R.drawable.non_active_dots
//                    )
//                )
//            }
//        }
//    }

}