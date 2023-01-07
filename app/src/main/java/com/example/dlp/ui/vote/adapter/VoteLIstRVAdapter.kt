package com.example.dlp.ui.vote.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dlp.R
import com.example.dlp.VoteData
import com.example.dlp.databinding.ItemVoteCategoryBinding
import java.security.AccessController.getContext
import kotlin.coroutines.coroutineContext


class VoteLIstRVAdapter (private val VoteList: ArrayList<VoteData>, val itemClick: (VoteData) -> Unit) : RecyclerView.Adapter<VoteLIstRVAdapter.VoteDataViewHolder>(){
    var selectPrefer = 0
    var preferArray = IntArray(11, { 0 })
    inner class VoteDataViewHolder( val viewBinding: ItemVoteCategoryBinding, val itemClick: (VoteData) -> Unit) : RecyclerView.ViewHolder(viewBinding.root){
        // val btnGood = itemView?.findViewById<Button>(R.id.btn_good)
        private val context = viewBinding.root.context
        fun bind(data: VoteData) = viewBinding.apply{
            tvVoteCategory.text = data.title

            //btnGood = itemView.findViewById(R.id.btn_good)
                //viewBinding.btnBad.setOnClickListener{

                    //viewBinding.btnBad.setColorFilter(ContextCompat.getColor(context, R.color.error), android.graphics.PorterDuff.Mode.MULTIPLY)
                    //viewBinding.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.red_option));
                    //viewBinding.tvVoteCategory.setTextColor(0xffffff)
                //}
                //viewBinding.btnGood.setOnClickListener{
                   // selectPrefer = 1
                   // viewBinding.btnGood.setColorFilter(ContextCompat.getColor(context, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                   // viewBinding.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.green_option));
                    //viewBinding.tvVoteCategory.setTextColor(0xffffff)
              //  }

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
        // btnGood click -> 배열의 값 1
        holder.viewBinding.btnGood.setOnClickListener {
            when(preferArray[position]) {
                0 -> {
                    Log.i("test","none")
                        preferArray[position] = 1
                        holder.viewBinding.btnGood.setImageResource(R.drawable.ic_thumb_up_clicked)
                    // 이미지 필터 변경(누른상태)
                }
                1 -> {
                    Log.i("test","cancel")
                    preferArray[position] = 0
                    holder.viewBinding.btnGood.setImageResource(R.drawable.ic_thumb_up)
                    // 배열 값 0으로 바꾸기
                    // 이미지 필터 변경(안눌린상태)

                }
                -3 -> {
                    Log.i("test","click")
                    preferArray[position] = 1
                    holder.viewBinding.btnGood.setImageResource(R.drawable.ic_thumb_up_clicked)
                    holder.viewBinding.btnBad.setImageResource(R.drawable.ic_thumb_down)
                    // 배열 값 1 로 바꾸기(
                    // 이미지 필터 변경(누른상태)
                    // btnBad이미지 필터 변경(안눌린상태)
                }
            }
        }

        holder.viewBinding.btnBad.setOnClickListener {
            when(preferArray[position]) {
                0 -> {
                    Log.i("test","none")
                    preferArray[position] = -3
                    holder.viewBinding.btnBad.setImageResource(R.drawable.ic_thumb_down_clicked)
                    // 이미지 필터 변경(누른상태)
                }
                -3 -> {
                    Log.i("test","cancel")
                    preferArray[position] = 0
                    holder.viewBinding.btnBad.setImageResource(R.drawable.ic_thumb_down)
                    // 배열 값 0으로 바꾸기
                    // 이미지 필터 변경(안눌린상태)

                }
                1 -> {
                    Log.i("test","click")
                    preferArray[position] = -3
                    holder.viewBinding.btnBad.setImageResource(R.drawable.ic_thumb_down_clicked)
                    holder.viewBinding.btnGood.setImageResource(R.drawable.ic_thumb_up)
                    // 배열 값 1 로 바꾸기(
                    // 이미지 필터 변경(누른상태)
                    // btnBad이미지 필터 변경(안눌린상태)
                }
            }
        }

    }

    override fun getItemCount(): Int = VoteList.size
}