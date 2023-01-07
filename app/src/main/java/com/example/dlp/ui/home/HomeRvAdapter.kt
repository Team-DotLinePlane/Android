package com.example.dlp.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dlp.databinding.ItemGroupInfoBinding
import com.example.dlp.databinding.ItemMapCategoryBinding
import com.example.dlp.network.response.TeamMealTime

class HomeRvAdapter :
    ListAdapter<TeamMealTime, HomeRvAdapter.ViewHolder>(PlanDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGroupInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    private var onItemClickListener: ((TeamMealTime) -> Unit)? = null
    fun setOnItemClickListener(searchStr: (TeamMealTime) -> Unit) {
        onItemClickListener = searchStr
    }

    companion object {
        private val PlanDiffCallback = object : DiffUtil.ItemCallback<TeamMealTime>() {
            override fun areItemsTheSame(oldItem: TeamMealTime, newItem: TeamMealTime): Boolean {
                return oldItem.teamCode == newItem.teamCode
            }

            override fun areContentsTheSame(oldItem: TeamMealTime, newItem: TeamMealTime): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(
        private val binding: ItemGroupInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(TeamMealTime: TeamMealTime) {
            binding.teamNameTv.text = TeamMealTime.teamName
            binding.teamNumberTv.text = "${TeamMealTime.numOfMembers.toString()} 명"
        }
    }

}
