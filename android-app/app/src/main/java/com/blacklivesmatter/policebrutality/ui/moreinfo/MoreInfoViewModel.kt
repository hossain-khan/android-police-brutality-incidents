package com.blacklivesmatter.policebrutality.ui.moreinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MoreInfoViewModel @Inject constructor() : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is more info Fragment"
    }
    val text: LiveData<String> = _text
}