package com.example.dlp.ui.vote.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dlp.R
import com.example.dlp.VoteData
import com.example.dlp.databinding.ItemVoteCategoryBinding


class VoteLIstRVAdapter (private val VoteList: ArrayList<VoteData>, val itemClick: (VoteData) -> Unit) : RecyclerView.Adapter<VoteLIstRVAdapter.VoteDataViewHolder>(){
    var selectPos = -1
    inner class VoteDataViewHolder(private val viewBinding: ItemVoteCategoryBinding, val itemClick: (VoteData) -> Unit) : RecyclerView.ViewHolder(viewBinding.root){
        // val btnGood = itemView?.findViewById<Button>(R.id.btn_good)
        private val context = viewBinding.root.context
        fun bind(data: VoteData) = viewBinding.apply{
            tvVoteCategory.text = data.title

            //btnGood = itemView.findViewById(R.id.btn_good)
                viewBinding.btnBad.setOnClickListener{
                    Log.i("test","asdad")
                    viewBinding.btnBad.setColorFilter(ContextCompat.getColor(context, R.color.error), android.graphics.PorterDuff.Mode.MULTIPLY)
                    //viewBinding.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.red_option));
                    //viewBinding.tvVoteCategory.setTextColor(0xffffff)
                }
                viewBinding.btnGood.setOnClickListener{
                    viewBinding.btnGood.setColorFilter(ContextCompat.getColor(context,
                        R.color.green
                    ), android.graphics.PorterDuff.Mode.MULTIPLY);
                   // viewBinding.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.green_option));
                    //viewBinding.tvVoteCategory.setTextColor(0xffffff)
                }

        }
    }

    private var onItemClickListener: ((VoteData) -> Unit)? = null
    fun setOnItemClickListener(voteData: (VoteData) -> Unit) {
        onItemClickListener = voteData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoteDataViewHolder {
        val viewBinding = ItemVoteCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  VoteDataViewHolder(viewBinding, itemClick)
    }

    override fun onBindViewHolder(holder: VoteDataViewHolder, position: Int) {
        holder.bind(VoteList[position])
    }

    override fun getItemCount(): Int = VoteList.size
}