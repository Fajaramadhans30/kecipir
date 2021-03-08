package com.test.kecipirtest.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.ShimmerFrameLayout

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun AppCompatActivity.replaceFragment(layoutId:Int, fragment:Fragment, isBackStack:Boolean = false){
    supportFragmentManager
        .beginTransaction()
        .addToBackStack(if(isBackStack) null else fragment.tag)
        .replace(layoutId, fragment)
        .commit()
}

fun ImageView.showImageUrl(context:Context, url:String){
    Glide.with(context).load(url)
        .listener(object: RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Log.d("GAGAL", e?.message.toString())
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                Log.d("product", "load success")
                return false
            }

        })
        .into(this)
}

fun ShimmerFrameLayout.showLoading(){
    this.startShimmer()
}

fun ShimmerFrameLayout.hideLoading(){
    this.stopShimmer()
    this.gone()
}