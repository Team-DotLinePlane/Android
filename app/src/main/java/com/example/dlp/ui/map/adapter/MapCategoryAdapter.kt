package com.example.dlp.ui.map.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dlp.databinding.ItemMapCategoryBinding

class MapCategoryAdapter :
    ListAdapter<String, MapCategoryAdapter.ViewHolder>(PlanDiffCallback) {

    var selectedStr = DEFAULT_SELECTED

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMapCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchStr = currentList[position]
        holder.bind(searchStr)
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener?.invoke(searchStr)
            }
        }
    }

    private var onItemClickListener: ((String) -> Unit)? = null
    fun setOnItemClickListener(searchStr: (String) -> Unit) {
        onItemClickListener = searchStr
    }

    companion object {
        const val DEFAULT_SELECTED = "한식"
        private val PlanDiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(
        private val binding: ItemMapCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(str: String) {
            binding.searchTv.text = str
            if (selectedStr == str) {
                binding.searchTv.setTextColor(Color.parseColor("#3360FF"))
            } else {
                binding.searchTv.setTextColor(Color.parseColor("#AFAFAF"))
            }
        }
    }

}
