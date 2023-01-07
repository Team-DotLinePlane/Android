package com.example.dlp.ui.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dlp.databinding.ItemMapCategoryBinding

class MapCategoryAdapter :
    ListAdapter<String, MapCategoryAdapter.ViewHolder>(PlanDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMapCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = currentList[position]
        holder.bind(plan)
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener?.invoke(plan)
            }
        }
    }

    private var onItemClickListener: ((String) -> Unit)? = null
    fun setOnItemClickListener(action: (String) -> Unit) {
        onItemClickListener = action
    }

    companion object {
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
        }
    }
}
