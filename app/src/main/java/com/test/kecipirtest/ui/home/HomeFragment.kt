package com.test.kecipirtest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kennyc.view.MultiStateView
import com.test.kecipirtest.ProductCartActivity
import com.test.kecipirtest.ProductDetailActivity
import com.test.kecipirtest.ProductListActivity
import com.test.kecipirtest.R
import com.test.kecipirtest.groupie.BannerGroupieItem
import com.test.kecipirtest.groupie.CategoryGroupItem
import com.test.kecipirtest.groupie.HeaderGroupieItem
import com.test.kecipirtest.groupie.ProductGroupieItem
import com.test.kecipirtest.data.model.banner.Banner
import com.test.kecipirtest.data.model.category.Category
import com.test.kecipirtest.data.model.product.Product
import com.test.kecipirtest.network.remote.Resource
import com.test.kecipirtest.ui.adapter.CalendarAdapter
import com.test.kecipirtest.util.enum.ProductType
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.bottomsheet_date_area_delivery_layout.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val lastDayInCalendar = Calendar.getInstance(Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)

    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private val currentMonth = currentDate[Calendar.MONTH]
    private val currentYear = currentDate[Calendar.YEAR]

    private var selectedDay: Int = currentDay
    private var selectedMonth: Int = currentMonth
    private var selectedYear: Int = currentYear

    private val dates = ArrayList<Date>()

    private val groupieAdapter: GroupAdapter<GroupieViewHolder> by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val section : Section by lazy {
        Section()
    }

    private var bannerGroupieItem:BannerGroupieItem ? = null
    private var categoryGroupieItem:CategoryGroupItem ? = null
    private var productGroupieItem:ProductGroupieItem ? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGroupie()
        homeViewModel.getBanner()
        homeViewModel.getCategory()
        homeViewModel.getProducts()
        observeData()

        val bottomSheetBehavior = BottomSheetBehavior.from(constraintBottomSheet)
        constraint1.setOnClickListener {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED)
        }

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(calendar_recycler_view)

        lastDayInCalendar.add(Calendar.DAY_OF_MONTH, 6)
        setUpCalendar()

        imgCart.setOnClickListener {
            startAddToCart()
        }

    }

    private fun setUpCalendar() {
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        var currentPosition = 0
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        while (dates.size < maxDaysInMonth) {
            // get position of selected day
            if (monthCalendar[Calendar.DAY_OF_MONTH] == selectedDay)
                currentPosition = dates.size
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        // Assigning calendar view.
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        calendar_recycler_view!!.layoutManager = layoutManager
        val calendarAdapter = context?.let { CalendarAdapter(it, dates, currentDate) }
        calendar_recycler_view!!.adapter = calendarAdapter

        when {
            currentPosition > 2 -> calendar_recycler_view!!.scrollToPosition(currentPosition - 3)
            maxDaysInMonth - currentPosition < 2 -> calendar_recycler_view!!.scrollToPosition(currentPosition)
            else -> calendar_recycler_view!!.scrollToPosition(currentPosition)
        }
        calendarAdapter?.setOnItemClickListener(object : CalendarAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val clickCalendar = Calendar.getInstance()
                clickCalendar.time = dates[position]
                selectedDay = clickCalendar[Calendar.DAY_OF_MONTH]
            }
        })

    }

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? =
            (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        val supportActionBar: ActionBar? =
            (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.show()
    }

    private fun initGroupie(){
        bannerGroupieItem = BannerGroupieItem(requireContext(), mutableListOf()) {
//            toProductDetail(product)
        }

        /*CATEGORY*/
        categoryGroupieItem = CategoryGroupItem(requireContext(), mutableListOf()) {
            startProductList(ProductType.CATEGORY.type, it.toString())
        }

        /*PROMO*/
        productGroupieItem = ProductGroupieItem(requireContext(), mutableListOf()) {
            startProductDetail(it)
        }

        /*Favoritku*/
        productGroupieItem = ProductGroupieItem(requireContext(), mutableListOf()) {
            startProductDetail(it)
        }

        /*Produk Terlaris*/
        productGroupieItem = ProductGroupieItem(requireContext(), mutableListOf()) {
            startProductDetail(it)
        }

        /*Semua Produk*/
        productGroupieItem = ProductGroupieItem(requireContext(), mutableListOf()) {
            startProductDetail(it)

        }

        initHomeContent()
    }

    private fun initHomeContent(){
//        section.add(HeaderGroupieItem(getString(R.string.title_todays_discount)))
        section.add(bannerGroupieItem!!)
        section.add(categoryGroupieItem!!)

        section.add(HeaderGroupieItem(getString(R.string.promo)) {
            startProductList(ProductType.PROMO.type)
        })
        section.add(productGroupieItem!!)

        section.add(HeaderGroupieItem(getString(R.string.favoritku)) {
            startProductList(ProductType.FAVOURITE.type)
        })
        section.add(productGroupieItem!!)

        section.add(HeaderGroupieItem(getString(R.string.produk_terlaris)) {
            startProductList(ProductType.POPULAR.type)
        })
        section.add(productGroupieItem!!)

        section.add(HeaderGroupieItem(getString(R.string.semua_produk)) {
            startProductList(ProductType.ALL_PRODUCT.type)
        })
        section.add(productGroupieItem!!)


        groupieAdapter.add(section)

        rvHomeContent.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = groupieAdapter
        }
    }

    private fun observeData() {
        homeViewModel.bannners.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> showHomeLoading()
                is Resource.Empty -> showHomeEmpty()
                is Resource.Success -> showHomeContent(it.data)
                is Resource.Error -> showHomeError()
            }
        })

        homeViewModel.categorys.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> showHomeLoading()
                is Resource.Empty -> showHomeEmpty()
                is Resource.Success -> showHomeContentCategory(it.data)
                is Resource.Error -> showHomeError()
            }
        })

        homeViewModel.products.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> showHomeLoading()
                is Resource.Empty -> showHomeEmpty()
                is Resource.Success -> showHomeContentProduct(it.data)
                is Resource.Error -> showHomeError()
            }
        })
    }

    private fun showHomeLoading(){
        bannerGroupieItem?.viewState = MultiStateView.ViewState.LOADING
        categoryGroupieItem?.viewState = MultiStateView.ViewState.LOADING
        productGroupieItem?.viewState = MultiStateView.ViewState.LOADING
//        popularProductGroupieItem?.viewState = MultiStateView.ViewState.LOADING
//        allProductGroupieItem?.viewState = MultiStateView.ViewState.LOADING

        groupieAdapter.notifyDataSetChanged()
    }

    private fun showHomeEmpty(){
        bannerGroupieItem?.viewState = MultiStateView.ViewState.EMPTY
        categoryGroupieItem?.viewState = MultiStateView.ViewState.EMPTY
        productGroupieItem?.viewState = MultiStateView.ViewState.EMPTY
//        popularProductGroupieItem?.viewState = MultiStateView.ViewState.EMPTY
//        allProductGroupieItem?.viewState = MultiStateView.ViewState.EMPTY

        groupieAdapter.notifyDataSetChanged()
    }

    private fun showHomeError(){
        bannerGroupieItem?.viewState = MultiStateView.ViewState.ERROR
        categoryGroupieItem?.viewState = MultiStateView.ViewState.ERROR
        productGroupieItem?.viewState = MultiStateView.ViewState.ERROR
        groupieAdapter.notifyDataSetChanged()
    }

    private fun showHomeContent(datas: List<Banner>){
        bannerGroupieItem?.viewState = MultiStateView.ViewState.CONTENT
        showBannerItem(datas)
        groupieAdapter.notifyDataSetChanged()
    }


    private fun showHomeContentCategory(data: List<Category>) {
        categoryGroupieItem?.viewState = MultiStateView.ViewState.CONTENT

        showCategoryItem(data)

    }

    private fun showHomeContentProduct(data: List<Product>) {
        productGroupieItem?.viewState = MultiStateView.ViewState.CONTENT

        showProductItem(data)
    }


    private fun showBannerItem(datas:List<Banner>){
//        val banners = datas.filter { it.discount != "0%" }
        bannerGroupieItem?.add(datas)
    }

    private fun showCategoryItem(datas:List<Category>){
//        val banners = datas.filter { it.discount != "0%" }
        categoryGroupieItem?.add(datas)
    }

    private fun showProductItem(data: List<Product>) {
        productGroupieItem?.add(data)

    }

    private fun startProductList(productType :Int, category :String = ""){
        context?.let { ProductListActivity.start(it, productType, category) }
    }

    private fun startProductDetail(data:Product){
        ProductDetailActivity.start(requireContext(), data)
    }

    private fun startAddToCart() {
        ProductCartActivity.start(requireContext())

    }

}