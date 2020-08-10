package com.example.countriesapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countriesapp.model.Country
import com.example.countriesapp.service.ApiClient
import com.example.countriesapp.service.Database
import com.example.countriesapp.util.SharedPref
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedFragmentViewModel(application : Application) : BaseViewModel(application) {

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable() // after api calls, the calls will be destroyed (memory optimization)
    private var customSharedPref = SharedPref(getApplication())
    private val REFRESH_TIME = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData() {

        val updateTime = customSharedPref.getTime()

        if (updateTime != null && updateTime != 0L && (System.nanoTime() - updateTime) < REFRESH_TIME) {
            getDataFromDatabase()
        } else {
            getDataFromAPI()
        }
    }

    fun refreshFromAPI() {
        getDataFromAPI()
    }

    private fun getDataFromDatabase() {
        countryLoading.value = true
        launch {
            val countries = Database(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries from Database", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromAPI() {
        countryLoading.value = true

        disposable.add(
            apiClient.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeListToDatabase(t)
                        Toast.makeText(getApplication(), "Countries from API", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeListToDatabase(list : List<Country>) {
        launch {
            val dao = Database(getApplication()).countryDao()
            dao.deleteAllCountries()
            val countryIds = dao.insertAll(*list.toTypedArray()) // to typed array, all list elements sending one by one list -> individual

            var i = 0;

            while (i < list.size) {
                list[i].uuid = countryIds[i].toInt()
                i++
            }

            showCountries(list)
        }

        customSharedPref.lastDownloadCheckpoint(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}