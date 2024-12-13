package ir.kasebvatan.currency.domain

import ir.kasebvatan.currency.domain.model.Currency
import ir.kasebvatan.currency.domain.model.RequestState

interface CurrencyApiService {

    suspend fun getLatestExchangeRate(): RequestState<List<Currency >>

}