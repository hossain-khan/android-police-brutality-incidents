package com.blacklivesmatter.policebrutality.ui.newreport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class NewReportViewModel @Inject constructor() : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This report new incident Fragment"
    }
    val text: LiveData<String> = _text
}