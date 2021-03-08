package com.test.kecipirtest.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomViewDialog(context: Context) : Dialog(context), View.OnClickListener {


//    fun CustomListViewDialog(context: Context?, themeResId: Int) {
//        super(context, themeResId)
//    }
//
//    fun CustomListViewDialog(
//        context: Context?,
//        cancelable: Boolean,
//        cancelListener: DialogInterface.OnCancelListener?
//    ) {
//        super(context, cancelable, cancelListener)
//    }


    var activity: Activity? = null
    var dialog: Dialog? = null
    var yes: Button? = null
    var no:Button? = null
    var title: TextView? = null
    var recyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<*>? = null


    fun CustomListViewDialog(a: Activity?, adapter: RecyclerView.Adapter<*>?) {
        this.activity = a
        this.adapter = adapter
        setupLayout()
    }

    private fun setupLayout() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        setContentView(R.layout.custom_dialog_layout)
//        yes = findViewById(R.id.yes) as Button?
//        no = findViewById(R.id.no) as Button
//        title = findViewById(R.id.title)
//        recyclerView = findViewById(R.id.recycler_view)
//        mLayoutManager = LinearLayoutManager(activity)
//        recyclerView!!.layoutManager = mLayoutManager
//        recyclerView!!.adapter = adapter
//        yes.setOnClickListener(this)
//        no.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


//    override fun onClick(v: View) {
//        when (v.getId()) {
//            R.id.yes -> {
//            }
//            R.id.no -> dismiss()
//            else -> {
//            }
//        }
//        dismiss()
//    }
}