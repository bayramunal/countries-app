package com.example.countriesapp.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.countriesapp.R

fun ImageView.getImageFromUrl(url : String?, progressDrawable: CircularProgressDrawable) {

    println("incoming url : " + url)

//    val options = RequestOptions()
//        .placeholder(progressDrawable)
//        .error(R.mipmap.ic_launcher_round) // i search it this is not working // i didnt understand

    Glide.with(context)
        //.setDefaultRequestOptions(options)
        .load(url)
        .placeholder(R.mipmap.ic_launcher_round)
        .error(R.mipmap.ic_launcher_round)
        .into(this)
}

fun placeholderProgressBar(context : Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}