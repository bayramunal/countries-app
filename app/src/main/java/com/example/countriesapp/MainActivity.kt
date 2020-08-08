package com.example.countriesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btnToCountryFragmentClick (view: View?) {
        val routingAction = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
        view?.findNavController()?.navigate(routingAction)
    }

    fun btnToFeedFragmentClick (view: View?) {
        val routingAction = CountryFragmentDirections.actionCountryFragmentToFeedFragment()
        view?.findNavController()?.navigate(routingAction)
    }

}