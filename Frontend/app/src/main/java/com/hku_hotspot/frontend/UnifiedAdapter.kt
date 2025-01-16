package com.hku_hotspot.frontend

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng

data class UnifiedItem(val name: String, val status: String, val position: LatLng)

class UnifiedAdapter(
    private var items: List<UnifiedItem>,
    private val onItemSelected: (LatLng) -> Unit
) : RecyclerView.Adapter<UnifiedAdapter.UnifiedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnifiedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_unified, parent, false)
        return UnifiedViewHolder(view)
    }

    override fun onBindViewHolder(holder: UnifiedViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemSelected)
    }

    override fun getItemCount() = items.size

    class UnifiedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)
        private val itemStatusTextView: TextView = itemView.findViewById(R.id.item_status)

        fun bind(item: UnifiedItem, onItemSelected: (LatLng) -> Unit) {
            itemNameTextView.text = item.name
            itemStatusTextView.text = item.status

            // Set the background color and text color
            val (bgColor, textColor) = getOccupancyColors(item.status)
            itemStatusTextView.setBackgroundColor(bgColor)
            itemStatusTextView.setTextColor(textColor)

            itemView.setOnClickListener {
                onItemSelected(item.position)
            }
        }

        private fun getOccupancyColors(status: String): Pair<Int, Int> {
            return when (status) {
                "Empty" -> Pair(Color.argb(100, 0, 255, 0), Color.BLACK) // Light green bg, black text
                "Low" -> Pair(Color.argb(150, 173, 255, 47), Color.BLACK) // Green-yellow bg, black text
                "Moderate" -> Pair(Color.argb(200, 255, 255, 0), Color.BLACK) // Yellow bg, black text
                "High" -> Pair(Color.argb(250, 255, 165, 0), Color.BLACK) // Orange bg, black text
                "Full" -> Pair(Color.argb(255, 255, 0, 0), Color.WHITE) // Red bg, white text
                else -> Pair(Color.GRAY, Color.WHITE) // Default color for unknown statuses
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<UnifiedItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}