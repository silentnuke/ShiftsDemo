package com.silentnuke.shifts.addshift

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.silentnuke.shifts.R
import com.silentnuke.shifts.addshift.ItemsAdapter.ItemViewHolder

class ItemsAdapter(val selectedItem: MutableLiveData<String>) :
    ListAdapter<String, ItemViewHolder>(ShiftDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.add_shift_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItemPosition(item: String?): Int {
        for (pos in 0 until itemCount) {
            if (getItem(pos) == item) {
                return pos
            }
        }
        return RecyclerView.NO_POSITION
    }

    inner class ItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) {
            (itemView as TextView).text = item
            if (selectedItem.value == item) {
                itemView.setTextColor(Color.BLUE);
            } else {
                itemView.setTextColor(Color.GRAY);
            }
            itemView.setOnClickListener {
                if (selectedItem.value == item) {
                    return@setOnClickListener
                }

                val oldPos = getItemPosition(selectedItem.value)
                selectedItem.value = item
                notifyItemChanged(oldPos)
                notifyItemChanged(adapterPosition)
            }
        }
    }
}

class ShiftDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}
