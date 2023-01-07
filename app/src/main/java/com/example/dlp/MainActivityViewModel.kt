package com.example.dlp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val _selectedKeyword: MutableLiveData<String> = MutableLiveData("")
    val selectedKeyword: LiveData<String> get() = _selectedKeyword

    fun updateSelectedKeyword(str: String) {
        _selectedKeyword.value = str
    }
}