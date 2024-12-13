package ir.kasebvatan.currency.data.remote.api


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import ir.kasebvatan.currency.domain.CurrencyApiService
import ir.kasebvatan.currency.domain.model.ApiResponse
import ir.kasebvatan.currency.domain.model.Currency
import ir.kasebvatan.currency.domain.model.CurrencyCode
import ir.kasebvatan.currency.domain.model.PreferencesRepository
import ir.kasebvatan.currency.domain.model.RequestState
import kotlinx.serialization.json.Json

class CurrencyApiServiceImpl(
    private val preferences: PreferencesRepository
) : CurrencyApiService {
    companion object {
        const val ENDPOINT = "https://api.currencyapi.com/v3/latest"
        const val API_KEY = "cur_live_vyBnLKpniIlE8Un6MXtNp3DeaLbbLAsfvsuV8jsk"
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15_000
        }
        install(DefaultRequest) {
            headers {
                append("apikey", API_KEY)
            }
        }
    }

    override suspend fun getLatestExchangeRate(): RequestState<List<Currency>> {
        return try {

            val response = httpClient.get(ENDPOINT)
            println("Api Response: ${response.body<String>()}")
            if (response.status.value == 200) {
                val apiResponse = Json.decodeFromString<ApiResponse>(response.body())


                val availableCurrencyCode = apiResponse.data.keys.filter {
                    CurrencyCode.entries
                        .map { code -> code.name }
                        .toSet()
                        .contains(it)
                }

                val availableCurrencies = apiResponse.data.values
                    .filter { currency ->
                        availableCurrencyCode.contains(currency.code)
                    }


                val lastUpdate = apiResponse.meta.lastUpdatedAt
                preferences.saveLastUpdated(lastUpdate)


                RequestState.Success(data = availableCurrencies)
            } else {
                RequestState.Error(message = "HTTP Error Code: ${response.status}")
            }

        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }
}
