package com.artem.currencyconverterapp.main

import com.artem.currencyconverterapp.CurrencyAPI
import com.artem.currencyconverterapp.model.CurrencyRatesDTO
import com.artem.currencyconverterapp.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: CurrencyAPI
) : MainRepository {
    override suspend fun getRates(base: String, symbols: String): Resource<CurrencyRatesDTO> {
        return try {
            val response = api.getRates(base, symbols)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)

            } else {
                Resource.Error(response.message())
            }

        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something went wrong")
        }
    }
}