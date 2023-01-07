package com.example.dlp

import BottomSheetDialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.dlp.databinding.FragHomeBinding
import com.example.dlp.ui.dialogs.InsertGroupCodeDialogInterface
import com.example.dlp.ui.vote.VoteActivity

class HomeFragment : Fragment(), InsertGroupCodeDialogInterface {
    lateinit var binding: FragHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_home, container, false)
        initClickListener()

        binding.tvEmptygroup.setOnClickListener {
            val intent = Intent(context, VoteActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun initClickListener() {
        binding.apply {
            btnParticipateGroup.setOnClickListener {
                val bottomSheet = BottomSheetDialog(this@HomeFragment)
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
            btnCreategroup.setOnClickListener {
                (activity as MainActivity).replaceFramgent(com.example.dlp.ui.creategroup.CreateGroupFragment())
            }
        }
    }

    override fun onCompleteButtonClicked(content: String) {

        Log.e("BottomSheet 입력 :", content)
    }
}