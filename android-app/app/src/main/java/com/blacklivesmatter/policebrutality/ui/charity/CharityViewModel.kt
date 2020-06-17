package com.blacklivesmatter.policebrutality.ui.charity

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blacklivesmatter.policebrutality.analytics.Analytics
import com.blacklivesmatter.policebrutality.data.CharityDao
import com.blacklivesmatter.policebrutality.data.model.CharityOrg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class CharityViewModel @ViewModelInject constructor(
    private val charityDao: CharityDao,
    private val analytics: Analytics
) : ViewModel() {
    private val _charityList = MutableLiveData<List<CharityOrg>>()
    val charityList: LiveData<List<CharityOrg>> = _charityList

    init {
        loadCharityListy()
    }

    private fun loadCharityListy() {
        viewModelScope.launch(Dispatchers.IO) {
            val charities = charityDao.getCharities()
            Timber.d("Got total ${charities.size} charities from DAO.")
            _charityList.postValue(charities)
        }
    }

    fun onCharitySelected(charity: CharityOrg) {
        analytics.logSelectItem(Analytics.CONTENT_TYPE_CHARITY, charity.org_url, charity.name)
    }
}
