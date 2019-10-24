package com.silentnuke.shifts.shifts

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.silentnuke.shifts.R
import com.silentnuke.shifts.data.Shift
import com.silentnuke.shifts.shifts.ShiftsAdapter.ShiftViewHolder
import kotlinx.android.synthetic.main.shift_item.view.*
import java.text.SimpleDateFormat
import java.util.Locale


class ShiftsAdapter : ListAdapter<Shift, ShiftViewHolder>(ShiftDiffCallback()) {
    var inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US)
    var outputDateFormat = SimpleDateFormat("EEE, MMM d h:mm a", Locale.US)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.shift_item, parent, false)
        return ShiftViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShiftViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ShiftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Shift) {
            val date = outputDateFormat.format(inputDateFormat.parse(item.startDate))
            itemView.textView.text = "${item.name} (${item.role}) $date"
            itemView.colorView.setBackgroundColor(Color.parseColor(item.color))
        }

    }
}

class ShiftDiffCallback : DiffUtil.ItemCallback<Shift>() {
    override fun areItemsTheSame(oldItem: Shift, newItem: Shift): Boolean {
        return oldItem.name == newItem.name
                && oldItem.role == newItem.role
                && oldItem.startDate == newItem.startDate
                && oldItem.endDate == newItem.endDate
                && oldItem.color == newItem.color
    }

    override fun areContentsTheSame(oldItem: Shift, newItem: Shift): Boolean {
        return oldItem == newItem
    }

}
