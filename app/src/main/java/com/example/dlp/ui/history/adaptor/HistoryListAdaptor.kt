package com.example.dlp.ui.history.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dlp.HistoryData
import com.example.dlp.databinding.HistoryListItemBinding


class HistoryListAdaptor(
    private val HistoryList: ArrayList<HistoryData>,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<HistoryListAdaptor.HistoryDataViewHolder>() {
    inner class HistoryDataViewHolder(private val viewBinding: HistoryListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(data: HistoryData) = with(viewBinding) {
            tvHistoryDate.text = data.date
            tvHistoryCat.text = data.category
            tvHistoryMenu.text = data.menu
            btnArrow.setOnClickListener {
                onItemClicked(data.menu)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryDataViewHolder {
        val viewBinding =
            HistoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryDataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: HistoryDataViewHolder, position: Int) {
        holder.bind(HistoryList[position])
    }

    override fun getItemCount(): Int = HistoryList.size
}