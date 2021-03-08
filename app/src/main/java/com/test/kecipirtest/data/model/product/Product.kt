package com.test.kecipirtest.data.model.product


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("code")
    val code: String,
    @SerializedName("discount")
    val discount: String,
    @SerializedName("farmer")
    val farmer: String,
    @SerializedName("grade")
    val grade: String,
    @SerializedName("grade_color")
    val gradeColor: String,
    @SerializedName("grade_note")
    val gradeNote: String,
    @SerializedName("harvest_date")
    val harvestDate: String,
    @SerializedName("id_harvest")
    val idHarvest: String,
    @SerializedName("id_product_farmer")
    val idProductFarmer: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("promo_price")
    val promoPrice: String,
    @SerializedName("qty_cart")
    val qtyCart: Int,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("sell_price")
    val sellPrice: String,
    @SerializedName("share_link")
    val shareLink: String,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_en")
    val titleEn: String,
    @SerializedName("unit")
    val unit: String
) : Parcelable