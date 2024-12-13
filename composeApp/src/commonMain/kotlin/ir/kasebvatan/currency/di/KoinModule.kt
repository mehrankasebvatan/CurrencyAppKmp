package ir.kasebvatan.currency.di

import com.russhwolf.settings.Settings
import ir.kasebvatan.currency.data.local.MongoImpl
import ir.kasebvatan.currency.data.local.PreferencesImpl
import ir.kasebvatan.currency.data.remote.api.CurrencyApiServiceImpl
import ir.kasebvatan.currency.domain.CurrencyApiService
import ir.kasebvatan.currency.domain.model.MongoRepository
import ir.kasebvatan.currency.domain.model.PreferencesRepository
import ir.kasebvatan.currency.presentation.screen.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single { Settings() }
    single<MongoRepository> { MongoImpl() }
    single<PreferencesRepository> { PreferencesImpl(setting = get()) }
    single<CurrencyApiService> { CurrencyApiServiceImpl(preferences = get()) }
    factory {
        HomeViewModel(
            preference = get(),
            mongoDb = get(),
            api = get()
        )
    }
}


fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}