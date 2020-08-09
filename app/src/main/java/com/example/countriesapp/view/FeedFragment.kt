package com.example.countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriesapp.R
import com.example.countriesapp.adapter.CountryAdapter
import com.example.countriesapp.model.Country
import com.example.countriesapp.viewmodel.FeedFragmentViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    private lateinit var viewModel : FeedFragmentViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedFragmentViewModel::class.java)
        viewModel.refreshData()

        rvCountryList.layoutManager = LinearLayoutManager(context)
        rvCountryList.adapter = countryAdapter

        observeLiveData()
    }

    fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                rvCountryList.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                tvCountryErrorMessage.visibility = View.GONE
                countryAdapter.updateCountryList(countries)
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    tvCountryErrorMessage.visibility = View.VISIBLE
                } else {
                    tvCountryErrorMessage.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    progressBar.visibility = View.VISIBLE
                    rvCountryList.visibility = View.GONE
                    tvCountryErrorMessage.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE

                }
            }
        })

    }

}