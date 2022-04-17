package com.artem.currencyconverterapp.main

import com.artem.currencyconverterapp.model.CurrencyRatesDTO
import com.artem.currencyconverterapp.utils.Resource

interface MainRepository {
    suspend fun getRates(base:String,symbols:String): Resource<CurrencyRatesDTO>

}