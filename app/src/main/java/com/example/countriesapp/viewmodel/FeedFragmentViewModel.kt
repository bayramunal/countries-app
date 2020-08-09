package com.example.countriesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countriesapp.model.Country

class FeedFragmentViewModel : ViewModel() {

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()


    fun refreshData() {
        val country1 = Country("Turkey", "Asia", "Ankara", "TRY", "Turkish", "www.aa.com")
        val country2= Country("France", "Europe", "Paris", "Euro", "French", "www.aa.com")
        val country3 = Country("Germany", "Europa", "Berlin", "Euro", "German", "www.aa.com")

        val countryList = arrayListOf<Country>(country1, country2, country3)

        countries.value = countryList
        countryError.value = false
        countryLoading.value = true
    }

}