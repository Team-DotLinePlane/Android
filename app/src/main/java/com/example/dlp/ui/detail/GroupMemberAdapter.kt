package com.example.dlp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dlp.databinding.ItemMemberBinding
import com.example.dlp.network.response.MemberResponse


class GroupMemberAdapter :
    ListAdapter<MemberResponse, GroupMemberAdapter.ViewHolder>(PlanDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    private var onItemClickListener: ((MemberResponse) -> Unit)? = null
    fun setOnItemClickListener(searchStr: (MemberResponse) -> Unit) {
        onItemClickListener = searchStr
    }

    companion object {
        private val PlanDiffCallback = object : DiffUtil.ItemCallback<MemberResponse>() {
            override fun areItemsTheSame(oldItem: MemberResponse, newItem: MemberResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MemberResponse, newItem: MemberResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(
        private val binding: ItemMemberBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(member: MemberResponse) {
            binding.nameTv.text = member.nickname
        }
    }

}