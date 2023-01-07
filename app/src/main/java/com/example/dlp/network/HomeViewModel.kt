package com.example.dlp.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dlp.network.response.Team

class HomeViewModel : ViewModel() {

    private val _groups: MutableLiveData<List<Team>> = MutableLiveData(emptyList())
    val groups: LiveData<List<Team>> get() = _groups

    fun setGroup(body: List<Team>) {
        _groups.value = body
    }


}