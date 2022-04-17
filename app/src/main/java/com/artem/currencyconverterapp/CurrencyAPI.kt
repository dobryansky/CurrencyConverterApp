package com.artem.currencyconverterapp

import com.artem.currencyconverterapp.model.CurrencyRatesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {

    @GET("/latest")
    suspend fun getRates(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Response<CurrencyRatesDTO>


}


