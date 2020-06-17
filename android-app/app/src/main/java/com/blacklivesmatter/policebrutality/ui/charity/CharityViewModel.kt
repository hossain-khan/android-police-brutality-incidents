package com.blacklivesmatter.policebrutality.ui.charity

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
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
    private val analytics: Analytics,
    private val preferences: SharedPreferences
) : ViewModel(), LifecycleObserver {
    companion object {
        private const val PREF_KEY_CHARITY_LIST_DISCLAIMER_INFO_SHOWN = "preference_key_charity_disclaimer_shown"
    }

    private val _shouldShowCharityDisclaimerInfoMessage = MutableLiveData<Unit>()
    val shouldShowCharityDisclaimerInfoMessage: LiveData<Unit> = _shouldShowCharityDisclaimerInfoMessage

    private val _charityList = MutableLiveData<List<CharityOrg>>()
    val charityList: LiveData<List<CharityOrg>> = _charityList

    init {
        loadCharityListy()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onViewStarted() {
        val isMessageShown = preferences.getBoolean(PREF_KEY_CHARITY_LIST_DISCLAIMER_INFO_SHOWN, false)
        if (isMessageShown.not()) {
            Timber.d("User has launched app for first time, show charity message.")
            preferences.edit().putBoolean(PREF_KEY_CHARITY_LIST_DISCLAIMER_INFO_SHOWN, true).apply()
            _shouldShowCharityDisclaimerInfoMessage.value = Unit
        } else {
            Timber.d("Not first time app launch, don't show charity message.")
        }
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
