package com.artem.currencyconverterapp.model


import com.google.gson.annotations.SerializedName

data class Rates(
    @SerializedName("AUD")
    val aUD: Double,
    @SerializedName("BRL")
    val bRL: Double,
    @SerializedName("BYN")
    val bYN: Double,
    @SerializedName("CNY")
    val cNY: Double,
    @SerializedName("CZK")
    val cZK: Double,
    @SerializedName("EUR")
    val eUR: Double,
    @SerializedName("GBP")
    val gBP: Double,
    @SerializedName("HKD")
    val hKD: Double,
    @SerializedName("IDR")
    val iDR: Double,
    @SerializedName("ILS")
    val iLS: Double,
    @SerializedName("JPY")
    val jPY: Double,
    @SerializedName("KZT")
    val kZT: Double,
    @SerializedName("PLN")
    val pLN: Double,
    @SerializedName("RUB")
    val rUB: Double,
    @SerializedName("USD")
    val uSD: Double
)