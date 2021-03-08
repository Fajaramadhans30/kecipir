package com.test.kecipirtest

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.kecipirtest.data.realm.ProductRealm
import com.test.kecipirtest.ui.adapter.CartAdapter
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_cart_list_product_layout.*
import kotlinx.android.synthetic.main.title_toolbar_layout.*
import org.jetbrains.anko.startActivity

class ProductCartActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private lateinit var productCart: ArrayList<ProductRealm>

    companion object {

        fun start(context: Context) {
            context.startActivity<ProductCartActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list_product_layout)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.title_toolbar_layout)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        action_bar_title?.text = getString(R.string.pembayaran)

        productCart = ArrayList()
        productCart.clear()
        getCart(productCart)
    }

    private fun getCart(productCart: ArrayList<ProductRealm>) {
        /*REALM*/
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("Cart.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(configuration)
        realm = Realm.getDefaultInstance()

        val result: RealmResults<ProductRealm> = realm.where<ProductRealm>(ProductRealm::class.java).findAll()

        val cartAdapter = CartAdapter(
            context = this,
            items =  result
        )
        rvListCartProduct.apply {
            layoutManager = LinearLayoutManager(this@ProductCartActivity, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}