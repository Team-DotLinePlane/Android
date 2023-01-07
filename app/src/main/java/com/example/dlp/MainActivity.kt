package com.example.dlp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dlp.databinding.ActivityMainBinding
import com.example.dlp.network.NetworkModule.userService
import com.example.dlp.network.request.SaveTokenRequest
import com.example.dlp.ui.history.HistoryFragment
import com.example.dlp.ui.map.MapFragment
import com.example.dlp.util.PreferenceUtil
import com.example.dlp.util.PreferenceUtil.EMPTY_TEXT
import com.example.dlp.util.PreferenceUtil.FCM_TOKEN_TEXT
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        FirebaseMessaging.getInstance().token.addOnSuccessListener { instanceIdResult ->
            val token: String = instanceIdResult.toString()
            // send it to server
            Log.i("FCM", token)
            val pref = PreferenceUtil
            if (pref.getString(FCM_TOKEN_TEXT, EMPTY_TEXT) == EMPTY_TEXT) {
                pref.setString(FCM_TOKEN_TEXT, token)
                userService.saveToken(SaveTokenRequest(token)).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.i("Http Response", response.toString())
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }
        }

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
                        checkLocationPermission {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(binding.frameLayout.id, MapFragment())
                                .commitAllowingStateLoss()
                            Log.e("myLog", "actionmap")
                        }
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

    fun replaceToMap(fragment: Fragment) {
        binding.navigationView.selectedItemId = R.id.action_map
    }

    private fun checkLocationPermission(onSucces: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onSucces()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ),
                1
            )
        }
    }

    fun replaceFramgent(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.frameLayout.id, fragment)
            .commitAllowingStateLoss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(binding.frameLayout.id, MapFragment())
                    .commitAllowingStateLoss()
            } else {
                Toast.makeText(this, "권한에 동의하지 않을 경우 이용할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

