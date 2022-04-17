package com.artem.currencyconverterapp.main


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.currencyconverterapp.model.Rates
import com.artem.currencyconverterapp.utils.DispatcherProvider
import com.artem.currencyconverterapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round
private const val SYMBOLS = "RUB,EUR,USD,BYN,PLN,GBP,KZT,ILS,CZK,AUD,BRL,HKD,IDR,CNY,JPY"
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider

) : ViewModel() {

    sealed class CurrencyEvent {
        class Success(val resultText: String): CurrencyEvent()
        class Failure(val errorText: String): CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if(fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when(val ratesResponse = repository.getRates(fromCurrency,  SYMBOLS)) {
                is Resource.Error -> _conversion.value = CurrencyEvent.Failure(ratesResponse.message!!)
                is Resource.Success -> {
                    val rates = ratesResponse.data!!.rates
                    val rate = getRateForCurrency(toCurrency, rates)
                    if(rate == null) {
                        _conversion.value = CurrencyEvent.Failure("Unexpected error")
                    } else {
                        val convertedCurrency = round(fromAmount * rate * 100) / 100
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                        )
                    }
                }
            }
        }
    }

    //https://api.exchangerate.host/latest?base=usd&symbols=RUB,EUR,USD,BYN,PLN,GBP,KZT,ILS,CZK,AUD,BRL,HKD,IDR,CNY,JPY
    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "RUB" -> rates.rUB
        "EUR" -> rates.hKD
        "USD" -> rates.uSD
        "BYN" -> rates.eUR
        "PLN" -> rates.pLN
        "GBP" -> rates.gBP
        "KZT" -> rates.kZT
        "ILS" -> rates.iLS
        "CZK" -> rates.cZK
        "AUD" -> rates.aUD
        "BRL" -> rates.bRL
        "HKD" -> rates.hKD
        "IDR" -> rates.iDR
        "CNY" -> rates.cNY
        "JPY" -> rates.jPY
        else -> null
    }

}