package com.example.dlp.ui.vote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dlp.databinding.ActivityVoteDoneBinding

class VoteDoneActivity : AppCompatActivity() {
    private val viewBinding : ActivityVoteDoneBinding by lazy {
        ActivityVoteDoneBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }
}