package com.test.kecipirtest.data.model.category

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category (
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("meta_description")
    val meta_description: String,
    @SerializedName("block")
    val block: String,
    @SerializedName("countdown")
    val countdown: Int,
    @SerializedName("poster")
    val poster: String,
    @SerializedName("item")
    val item: Int,
    @SerializedName("ordering")
    val ordering: Int
) : Parcelable