package com.example.countriesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countriesapp.model.Country

class CountryFragmentViewModel : ViewModel() {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom() {
        val country = Country("Turkey",
            "Ankara",
            "Asia",
            "TRY",
            "Turkish",
            "www.aa.com")

        countryLiveData.value = country
    }

}