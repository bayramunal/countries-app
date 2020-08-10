package com.example.countriesapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.countriesapp.model.Country

@Dao
interface DAO {
    // Data Access Object

    @Insert
    suspend fun insertAll(vararg countries : Country) : List<Long>
    // suspend func               -> coroutine içerisinde çalıştırılır, pausable and resumable
    // vararg                     -> multiple country objects
    // Return paramter List<Long> -> returns primary keys

    @Query("SELECT * FROM country") // table name is class name (because we didnt change the table name with args)
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryID")
    suspend fun getCountry(countryID : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

}