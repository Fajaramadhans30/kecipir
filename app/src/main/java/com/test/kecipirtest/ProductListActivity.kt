package com.test.kecipirtest

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.kennyc.view.MultiStateView
import com.test.kecipirtest.data.model.product.Product
import com.test.kecipirtest.network.remote.Resource
import com.test.kecipirtest.ui.adapter.ProductAdapter
import com.test.kecipirtest.ui.home.HomeViewModel
import com.test.kecipirtest.util.enum.ProductType
import kotlinx.android.synthetic.main.activity_product_list_layout.*
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel


class ProductListActivity : AppCompatActivity() {

    companion object {
        const val PRODUCT_TYPE = "product_type"
        const val CATEGORY = "categroy"

        fun start(context: Context, productType: Int, selectedCategory:String) {
            context.startActivity<ProductListActivity>(
                PRODUCT_TYPE to productType,
                CATEGORY to selectedCategory
            )
        }
    }

    private var productType = ProductType.ALL_PRODUCT.type
    private var selectedCategory: String = ""

    private val productViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list_layout)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

//        val inflator = LayoutInflater.from(this)
//        val v: View = inflator.inflate(R.layout.header_actionbar_layout_title, null)
//
//        (v.findViewById(R.id.tvActionBarTitle) as TextView).text = this.title
//        this.actionBar?.customView = v


        productType = intent.getIntExtra(PRODUCT_TYPE, 4)
        selectedCategory = intent.getStringExtra(CATEGORY).orEmpty()

        actionBar?.title =
            when (productType) {
                ProductType.CATEGORY.type -> selectedCategory
                ProductType.PROMO.type -> getString(R.string.promo)
                ProductType.FAVOURITE.type -> getString(R.string.my_favourite)
                ProductType.POPULAR.type -> getString(R.string.popular_product)
                ProductType.ALL_PRODUCT.type -> getString(R.string.semua_produk)

                else -> getString(R.string.semua_produk)
            }

        productViewModel.getProducts()
        observeData()
        val builder = AlertDialog.Builder(this)
        lnSortBy.setOnClickListener {
            val listItems =
                arrayOf("Semua", "Terpopuler", "Terlaris", "Harga Trtinggi", "Harga Terendah")

            builder.setTitle("Urutkan berdasarkan")

//            val inflater = this.layoutInflater
//            val dialogView: View = inflater.inflate(R.layout.alert_dialog, null)
//            builder.setView(dialogView)
//            builder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->

                builder.setItems(
                    listItems
                ) { dialog, which ->
                    Toast.makeText(
                        this@ProductListActivity,
                        "Position: " + which + " Value: " + listItems[which],
                        Toast.LENGTH_LONG
                    ).show()
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()

//            }
        }
    }

    private fun observeData() {
        productViewModel.products.observe(this, Observer {
            when (it) {
                is Resource.Loading -> msvProductList.viewState = MultiStateView.ViewState.LOADING
                is Resource.Empty -> msvProductList.viewState = MultiStateView.ViewState.EMPTY
                is Resource.Success -> {
                    msvProductList.viewState = MultiStateView.ViewState.CONTENT
                    showProducts(it.data)
                }
                is Resource.Error -> msvProductList.viewState = MultiStateView.ViewState.ERROR
            }
        })
    }

    private fun showProducts(products: List<Product>) {
        val productAdapter = ProductAdapter(
            context = this,
            datas = when (productType) {
                ProductType.CATEGORY.type -> products.filter { it.grade == selectedCategory }
                ProductType.POPULAR.type -> products.filter { it.rating > 4 }
                else -> products
            },
            onProductClicked = { product ->
                startProductDetail(product)
            }
        )

        rvProduct.apply {
            layoutManager = GridLayoutManager(this@ProductListActivity, 2)
            adapter = productAdapter
        }
    }

    private fun startProductDetail(data: Product) {
        ProductDetailActivity.start(
            this,
            data
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}