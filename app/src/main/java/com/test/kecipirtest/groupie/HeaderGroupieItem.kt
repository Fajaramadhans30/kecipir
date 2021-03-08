package com.test.kecipirtest.groupie

import com.test.kecipirtest.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.header_layout_title.*

class HeaderGroupieItem (val title:String, val action:(()->Unit)? = null): Item(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder){
            tvTitle.text = title
            btnLihatSemua.setOnClickListener {
                action?.invoke()
            }
        }
    }

    override fun getLayout(): Int = R.layout.header_layout_title

}