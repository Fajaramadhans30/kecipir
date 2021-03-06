package com.test.kecipirtest.ui.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.test.kecipirtest.R
import kotlinx.android.synthetic.main.date_item_layout.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(private val context: Context,
                      private val data: ArrayList<Date>,
                      private val currentDate: Calendar): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    private var mListener: OnItemClickListener? = null
    private var index = -1
    // This is true only the first time you load the calendar.
    private var selectCurrentDate = true
    private val currentMonth = currentDate[Calendar.MONTH]
    private val currentYear = currentDate[Calendar.YEAR]
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private val selectedDay = currentDay
    private val selectedMonth = currentMonth


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.date_item_layout, parent, false), mListener!!)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss")
        val sdf2 = SimpleDateFormat("EEE MMM dd HH:mm:ss", Locale.ENGLISH)
        val cal = Calendar.getInstance()
        cal.time = data[position]

        /**
         * Set the year, month and day that is gonna be displayed
         */
        val displayMonth = cal[Calendar.MONTH]
        val displayYear= cal[Calendar.YEAR]
        val displayDay = cal[Calendar.DAY_OF_MONTH]

        /**
         * Set text to txtDayInWeek and txtDay.
         */
        try {
            val dayInWeek = sdf.parse(cal.time.toString())!!
            val month = sdf2.parse(cal.time.toString())!!
            sdf.applyPattern("EEE")
            sdf2.applyPattern("MMM")
            holder.txtDayInWeek!!.text = sdf.format(dayInWeek).toString()
            holder.txtMonth!!.text = sdf2.format(month).toString()

        } catch (ex: ParseException) {
            Log.v("Exception", ex.localizedMessage!!)
        }
        holder.txtDay!!.text = cal[Calendar.DAY_OF_MONTH].toString()

        /**
         * I think you can use "cal.after (currentDate)" and "cal == currentDate",
         * but it didn't work properly for me, so I used this longer version. Here I just ask
         * if the displayed date is after the current date or if it is current date, if so,
         * then you enable the item and it is possible to click on it, otherwise deactivate it.
         * The selectCurrentDate value is valid only at the beginning, it will be the current
         * day or the first day, for example when starting the application or changing the month.
         */
        if (displayYear >= currentYear)
            if (displayMonth >= currentMonth || displayYear > currentYear)
                if (displayDay >= currentDay || displayMonth > currentMonth || displayYear > currentYear) {
                    /**
                     * Invoke OnClickListener and make the item selected.
                     */
                    holder.linearLayout!!.setOnClickListener {
                        index = position
                        selectCurrentDate = false
                        holder.listener.onItemClick(position)
                        notifyDataSetChanged()
                    }

                    if (index == position)
                        makeItemSelected(holder)
                    else {
                        if (displayDay == selectedDay
                            && displayMonth == selectedMonth
                            && selectCurrentDate)
                            makeItemSelected(holder)
                        else
                            makeItemDefault(holder)
                    }
                } else makeItemDisabled(holder)
            else makeItemDisabled(holder)
        else makeItemDisabled(holder)
    }

    inner class ViewHolder(itemView: View, val listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        var txtDay = itemView.tvDate
        var txtDayInWeek = itemView.tvDay
        var txtMonth = itemView.tvMonth
        var linearLayout = itemView.lnCalendar
    }

    /**
     * OnClickListener.
     */
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    /**
     * This make the item disabled.
     */
    private fun makeItemDisabled(holder: ViewHolder) {
        holder.txtDay!!.setTextColor(ContextCompat.getColor(context, R.color.colorGray))
//        holder.txtDayInWeek!!.setTextColor(ContextCompat.getColor(context, R.color.themeColor2))
        holder.linearLayout!!.setBackgroundColor(Color.WHITE)
        holder.linearLayout!!.isEnabled = false
    }

    /**
     * This make the item selected.
     */
    private fun makeItemSelected(holder: ViewHolder) {
        holder.linearLayout!!.setBackgroundResource(R.drawable.textbox_style_greenopacity2)
        holder.linearLayout!!.isEnabled = false
    }

    /**
     * This make the item default.
     */
    private fun makeItemDefault(holder: ViewHolder) {
        holder.linearLayout!!.setBackgroundResource(R.drawable.textbox_style_no_bg_gray)

        holder.linearLayout!!.isEnabled = true
    }
}