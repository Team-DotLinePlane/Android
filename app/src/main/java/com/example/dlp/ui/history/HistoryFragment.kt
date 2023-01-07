package com.example.dlp.ui.history

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dlp.*
import com.example.dlp.databinding.FragHistoryBinding
import com.example.dlp.network.NetworkModule.userService
import com.example.dlp.network.UserService
import com.example.dlp.network.request.ModifyNicknamRequest
import com.example.dlp.network.response.GetUerInfoReponse
import com.example.dlp.ui.history.adaptor.HistoryListAdaptor
import com.example.dlp.ui.map.MapFragment
import com.example.dlp.util.PreferenceUtil
import com.example.dlp.util.PreferenceUtil.EMPTY_TEXT
import com.example.dlp.util.PreferenceUtil.FCM_TOKEN_TEXT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HistoryFragment : Fragment() {
    lateinit var binding: FragHistoryBinding
    private lateinit var activityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_history, container, false)
        activityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        getNickName()

        val list: ArrayList<HistoryData> = arrayListOf()
        list.apply {
            add(HistoryData("2023-01-07", "한식", "김치볶음밥"))
            add(HistoryData("2023-01-07", "한식", "김치볶음밥"))
            add(HistoryData("2023-01-07", "한식", "김치볶음밥"))
            add(HistoryData("2023-01-07", "한식", "김치볶음밥"))
            add(HistoryData("2023-01-07", "한식", "김치볶음밥"))
            add(HistoryData("2023-01-07", "한식", "김치볶음밥"))
        }

        val listManager = LinearLayoutManager.HORIZONTAL
        val listRVAdaptor = HistoryListAdaptor(list) { searchStr ->
            activityViewModel.updateSelectedKeyword(searchStr)
            (activity as MainActivity).replaceToMap(MapFragment())
        }
        binding.rvList.adapter = listRVAdaptor
        binding.rvList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.imageView4.setOnClickListener {
            showAlertWithTextInputLayout(requireContext())
        }

        return binding.root
    }

    private fun getNickName() {
        userService.getNicknameByToken(
            PreferenceUtil.getString(FCM_TOKEN_TEXT, EMPTY_TEXT)
        ).enqueue(object : Callback<GetUerInfoReponse> {
            override fun onResponse(
                call: Call<GetUerInfoReponse>,
                response: Response<GetUerInfoReponse>
            ) {
                val nickname = response.body()?.nickname ?: "익명"
                binding.tvNickname.text = nickname
            }

            override fun onFailure(call: Call<GetUerInfoReponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun showAlertWithTextInputLayout(context: Context) {
        val taskEditText = EditText(context)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(50, 0, 50, 0)
        taskEditText.layoutParams = lp
        val dialog = AlertDialog.Builder(context)
            .setTitle("닉네임 재설정")
            .setMessage("재설정할 닉네임을 입력해주세요.")
            .setView(taskEditText)
            .setPositiveButton("확인") { dialog, which ->
                val modifiedNickname = taskEditText.text.toString()
                userService.modifyNickname(
                    ModifyNicknamRequest(
                        modifiedNickname, PreferenceUtil.getString(FCM_TOKEN_TEXT, EMPTY_TEXT)
                    )
                ).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "닉네임이 수정되었습니다.", Toast.LENGTH_SHORT)
                                .show()
                            getNickName()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
            }
            .setNegativeButton("취소", null)
            .create()
        dialog.show()
    }

}

