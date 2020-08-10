package com.example.countriesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesapp.R
import com.example.countriesapp.model.Country
import com.example.countriesapp.util.getImageFromUrl
import com.example.countriesapp.util.placeholderProgressBar
import com.example.countriesapp.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.tvCountryName.text = countryList[position].countryName
        holder.view.tvCountryRegion.text = countryList[position].countryRegion

        holder.view.setOnClickListener {
            val routingAction = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            it.findNavController().navigate(routingAction)
        }

        holder.view.ivCountryImage.getImageFromUrl(countryList[position].countryFlagUrl, placeholderProgressBar(holder.view.context))
    }

    fun updateCountryList (newCountryList : List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    class CountryViewHolder(var view : View) : RecyclerView.ViewHolder(view) {

    }

}