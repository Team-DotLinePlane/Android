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


class VoteLIstRVAdapter(
    private val VoteList: ArrayList<VoteData>,
    val itemClick: (VoteData) -> Unit,
) : RecyclerView.Adapter<VoteLIstRVAdapter.VoteDataViewHolder>() {
    var selectPrefer = 0
    var preferArray = IntArray(12, { 0 })
    var preferList: MutableList<Int> = MutableList(12) { 0 }

    inner class VoteDataViewHolder(
        val viewBinding: ItemVoteCategoryBinding,
        val itemClick: (VoteData) -> Unit,
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        // val btnGood = itemView?.findViewById<Button>(R.id.btn_good)
        private val context = viewBinding.root.context
        fun bind(data: VoteData) = viewBinding.apply {
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
        val viewBinding =
            ItemVoteCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VoteDataViewHolder(viewBinding, itemClick)
    }

    override fun onBindViewHolder(holder: VoteDataViewHolder, position: Int) {
        holder.bind(VoteList[position])
        // btnGood click -> ????????? ??? 1
        holder.viewBinding.btnGood.setOnClickListener {
            Log.i("preferList", preferList.toString())
            when (preferList[position]) {
                0 -> {
                    Log.i("test", "none position $position")
                    preferList[position] = 1
                    holder.viewBinding.btnGood.setImageResource(R.drawable.ic_thumb_up_clicked)
                    // ????????? ?????? ??????(????????????)
                }
                1 -> {
                    Log.i("test", "cancel position $position")
                    preferList[position] = 0
                    holder.viewBinding.btnGood.setImageResource(R.drawable.ic_thumb_up)
                    // ?????? ??? 0?????? ?????????
                    // ????????? ?????? ??????(???????????????)

                }
                -3 -> {
                    Log.i("test", "click position $position")
                    preferList[position] = 1
                    holder.viewBinding.btnGood.setImageResource(R.drawable.ic_thumb_up_clicked)
                    holder.viewBinding.btnBad.setImageResource(R.drawable.ic_thumb_down)
                    // ?????? ??? 1 ??? ?????????(
                    // ????????? ?????? ??????(????????????)
                    // btnBad????????? ?????? ??????(???????????????)
                }
            }
        }

        holder.viewBinding.btnBad.setOnClickListener {
            Log.i("preferList", preferList.toString())
            when (preferList[position]) {
                0 -> {
                    Log.i("test", "bad_non position $position")
                    preferList[position] = -3
                    holder.viewBinding.btnBad.setImageResource(R.drawable.ic_thumb_down_clicked)
                    // ????????? ?????? ??????(????????????)
                }
                -3 -> {
                    Log.i("test", "bad_cancel position $position")
                    preferList[position] = 0
                    holder.viewBinding.btnBad.setImageResource(R.drawable.ic_thumb_down)
                    // ?????? ??? 0?????? ?????????
                    // ????????? ?????? ??????(???????????????)

                }
                1 -> {
                    Log.i("test", "bad_click position $position")
                    preferList[position] = -3
                    holder.viewBinding.btnBad.setImageResource(R.drawable.ic_thumb_down_clicked)
                    holder.viewBinding.btnGood.setImageResource(R.drawable.ic_thumb_up)
                    // ?????? ??? 1 ??? ?????????(
                    // ????????? ?????? ??????(????????????)
                    // btnBad????????? ?????? ??????(???????????????)
                }
            }
        }

    }

    override fun getItemCount(): Int = VoteList.size
}