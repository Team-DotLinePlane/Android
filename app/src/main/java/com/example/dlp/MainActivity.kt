package com.example.dlp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.dlp.databinding.ActivityMainBinding
import com.example.dlp.ui.map.MapFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(binding.frameLayout.id, HomeFragment())
            .commitAllowingStateLoss()

        binding.navigationView.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.action_home -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.frameLayout.id, HomeFragment())
                            .commitAllowingStateLoss()
                        Log.e("myLog", "actionHome")
                    }
                    R.id.action_map -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.frameLayout.id, MapFragment())
                            .commitAllowingStateLoss()
                        Log.e("myLog", "actionmap")

                    }
                    R.id.action_history -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.frameLayout.id, HistoryFragment())
                            .commitAllowingStateLoss()

                    }
                }
                true
            }
            selectedItemId = R.id.action_home
        }
    }
}