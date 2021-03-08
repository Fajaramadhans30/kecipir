package com.test.kecipirtest.data.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ProductRealm (
    @PrimaryKey
    var id: Int? = null,
    var photo: String? = null,
    var farmer: String? = null,
    var title: String? = null,
    var sell_price: String? = null,
    var promo_price: String? = null,
    var unit: String? = null,
    var grade: String? = null,
    var discount: String? = null,
    var stock: String? = null,
    var totalItem: Int? = null
) : RealmObject()