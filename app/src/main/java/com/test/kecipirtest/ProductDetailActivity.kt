package com.test.kecipirtest

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kennyc.view.MultiStateView
import com.skydoves.whatif.whatIfNotNull
import com.test.kecipirtest.data.model.product.Product
import com.test.kecipirtest.data.realm.ProductRealm
import com.test.kecipirtest.network.remote.Resource
import com.test.kecipirtest.ui.adapter.ProductAdapter
import com.test.kecipirtest.ui.adapter.ProductAdapter.Companion.FEW_PRODUCT
import com.test.kecipirtest.ui.home.HomeViewModel
import com.test.kecipirtest.util.showImageUrl
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_product_detail_layout.*
import kotlinx.android.synthetic.main.bottomsheet_buy.*
import kotlinx.android.synthetic.main.cart_layout.*
import kotlinx.android.synthetic.main.product_layout.*
import kotlinx.android.synthetic.main.title_toolbar_layout.*
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel


class ProductDetailActivity : AppCompatActivity() {

    var mTotalItem = 0
    private lateinit var realm: Realm

    companion object {
        private const val PRODUCT_KEY = "prdouct_key"

        fun start(context: Context, product: Product) {
            context.startActivity<ProductDetailActivity>(
                PRODUCT_KEY to product
            )
        }
    }

    private var product: Product? = null

    private val productVieModel: HomeViewModel by viewModel()

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail_layout)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.title_toolbar_layout)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        product = intent.getParcelableExtra(PRODUCT_KEY)

        action_bar_title?.text = product?.title

        showProductDetail()

        productVieModel.getProducts()
        observeData()

        val bottomSheetBehavior = BottomSheetBehavior.from(constraintBottomSheetBuy)
        buttonBuy.setOnClickListener {
            setBottomSheetBuy()
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)

//            val view: View =
//                layoutInflater.inflate(R.layout.bottomsheet_buy, null)
//
//            val dialog = BottomSheetDialog(this)
//            dialog.setContentView(view)
//            dialog.show()

        }

        btnLihatKeranjang.setOnClickListener {
            addTocart()
        }

    }

    private fun setBottomSheetBuy() {
        Log.d("MASUK SINI ", "setBottomSheetBuy: ${product?.sellPrice}")
        product.whatIfNotNull {
            product?.apply {
                val prices = String.format("%,d", product!!.sellPrice.toLong())
                ivProductPhotoBuy.showImageUrl(this@ProductDetailActivity, photo)
                tvProductNameBuy.text = title
                tvProductPriceBuy.text = "Rp.$prices"
                tvProductWeightBuy.text = unit
                tvTotalPrice.text = "Rp." + "0"

                addOrder()

            }
        }
    }

    private fun addOrder() {
        btn_decrease.setOnClickListener {
            mTotalItem += 1
            display(mTotalItem)
        }

        btn_increase.setOnClickListener {
            mTotalItem -= 1
            if (mTotalItem < 0) {
                mTotalItem = 0
            }
            display(mTotalItem)
        }
    }

    private fun display(number: Int) {
        tv_value.text = "" + number
        mTotalItem = number

        val prices: Int? = (product!!.sellPrice.toInt().times(number))
        val totalPrice = String.format("%,d", prices?.toLong())

        tvTotalPrice.text = "Rp.$totalPrice"
        if (number < 1) {
            count_tv.visibility = View.GONE
        } else {
            count_tv.visibility = View.VISIBLE
            count_tv.text = number.toString()
        }

        Log.d("BERAPA INI ? ", "display: " + totalPrice)

//        clickBasket(number)
    }

    private fun addTocart() {
        /*REALM*/
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("Cart.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(configuration)
        realm = Realm.getDefaultInstance()

        product.whatIfNotNull {
            product?.apply {
                saveDataToRealm(
                    it.photo,
                    it.title,
                    it.sellPrice,
                    it.unit,
                    it.farmer,
                    it.stock,
                    it.discount,
                    it.promoPrice,
                    mTotalItem
                )
            }
        }

    }

    private fun saveDataToRealm(
        photo: String,
        title: String,
        sellPrice: String,
        unit: String,
        farmer: String,
        stock: Int,
        discount: String,
        promoPrice: String,
        mTotalItem: Int
    ) {
        realm.beginTransaction()
        val currentIdNumber: Number? = realm.where(ProductRealm::class.java).max("id")
        val nextID: Int

        nextID = if (currentIdNumber == null) {
            1
        } else {
            currentIdNumber.toInt() + 1
        }

        val productRealm = ProductRealm()
        productRealm.id = nextID
        productRealm.photo = photo
        productRealm.title = title
        productRealm.sell_price = sellPrice
        productRealm.unit = unit
        productRealm.farmer = farmer
        productRealm.stock = stock.toString()
        productRealm.discount = discount
        productRealm.promo_price = promoPrice
        productRealm.totalItem = mTotalItem

        realm.copyToRealmOrUpdate(productRealm)
        realm.commitTransaction()

        startAddToCart()
    }

    private fun showProductDetail() {
        product?.apply {
            val prices = String.format("%,d", product!!.sellPrice.toLong())

            product.whatIfNotNull {
                if (it.stock < 10) {
                    lnStock.setBackgroundResource(R.drawable.textbox_style_no_bg_orange)
                    tvStock.setTextColor(this@ProductDetailActivity.resources.getColor(R.color.colorAccentOrange))
                } else {
                    lnStock.setBackgroundResource(R.drawable.textbox_style_no_bg_green)
                    tvStock.setTextColor(this@ProductDetailActivity.resources.getColor(R.color.colorAccent))
                }

                if (it.grade == "Perlakuan Organik ") {
                    lnGrades.setBackgroundResource(R.drawable.textbox_style_blueopacity)
                } else if (it.grade == "Organik Bersertifikat") {
                    lnGrades.setBackgroundResource(R.drawable.textbox_style_greenopacity)
                } else {
                    lnGrades.setBackgroundResource(R.drawable.textbox_style_orangeopacity)
                }

                ivProductPhoto.showImageUrl(this@ProductDetailActivity, photo)
                tvPoductName.text = title
                tvProductPrice.text = "Rp.$prices"
                tvWeightProduct.text = unit
                tvFarmName.text = farmer
                tvGrade.text = grade
                tvDescGrade.text = gradeNote
                tvDescLevel.text = titleEn
                tvStock.text = "Stock $stock"
                tvGrade.setTextColor(Color.parseColor(gradeColor))

            }

        }
    }

    private fun observeData() {
        productVieModel.products.observe(this, Observer {
            when (it) {
                is Resource.Loading -> msvProduct.viewState = MultiStateView.ViewState.LOADING
                is Resource.Empty -> msvProduct.viewState = MultiStateView.ViewState.EMPTY
                is Resource.Success -> {
                    msvProduct.viewState = MultiStateView.ViewState.CONTENT
                    showProducts(it.data)
                }
                is Resource.Error -> msvProduct.viewState = MultiStateView.ViewState.ERROR
            }
        })
    }

    private fun showProducts(products: List<Product>) {
        val productAdapter = ProductAdapter(
            context = this,
            datas = products,
            onProductClicked = { product ->
                toProductDetail(product)
            },
            type = FEW_PRODUCT
        )

        rvProduct.apply {
            layoutManager = LinearLayoutManager(
                this@ProductDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = productAdapter
        }
    }

    private fun toProductDetail(data: Product) {
        start(
            this,
            data
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.cart_count_menu_item -> startAddToCart()
        }
        return super.onOptionsItemSelected(item)
    }

    //
    private fun startAddToCart() {
        ProductCartActivity.start(this)

    }
}