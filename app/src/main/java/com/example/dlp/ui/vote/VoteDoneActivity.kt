package com.example.dlp.ui.vote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dlp.databinding.ActivityVoteDoneBinding

class VoteDoneActivity : AppCompatActivity() {
    private val viewBinding: ActivityVoteDoneBinding by lazy {
        ActivityVoteDoneBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 투표 끝났는지
        if (intent.getBooleanExtra("onEnd", false)) {
            viewBinding.layoutLoading.visibility = View.GONE
            viewBinding.layoutMenu.visibility = View.VISIBLE
            val menu = intent.getStringExtra("menu")
            viewBinding.tvResult.text = menu
        } else {
            viewBinding.layoutLoading.visibility = View.VISIBLE
            viewBinding.layoutMenu.visibility = View.GONE
        }

        setContentView(viewBinding.root)
    }
}