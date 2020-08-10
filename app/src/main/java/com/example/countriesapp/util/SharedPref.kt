package com.example.countriesapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPref {

    companion object {
        private val PREF_TIME_CHECKPOINT  = "time"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance : SharedPref? = null

        private val lock = Any()

        operator fun invoke (context : Context) : SharedPref = instance ?: synchronized(lock) {
            instance ?: createSharedPrefInstance(context).also {
                instance = it
            }
        }

        private fun createSharedPrefInstance(context : Context) : SharedPref {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPref()
        }

    }

    fun lastDownloadCheckpoint (time : Long) {
        sharedPreferences?.edit(commit = true) {
            putLong(PREF_TIME_CHECKPOINT, time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(PREF_TIME_CHECKPOINT, 0)

}