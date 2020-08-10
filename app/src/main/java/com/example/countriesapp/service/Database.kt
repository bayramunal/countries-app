package com.example.countriesapp.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.countriesapp.model.Country

@Database(entities = arrayOf(Country::class), version = 1)
abstract class Database : RoomDatabase() {

    abstract fun countryDao() : DAO

    // this class object must be unique so we must use singleton pattern

    companion object {

        // different threads see this @Volatile
        @Volatile private var instance : com.example.countriesapp.service.Database? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock) {
            instance ?: createDatabaseInstance(context).also {
                instance = it
            }
        }

        private fun createDatabaseInstance(context : Context) = Room.databaseBuilder(
            context.applicationContext, com.example.countriesapp.service.Database::class.java, "countryDatabase"
        ).build()

    }

}