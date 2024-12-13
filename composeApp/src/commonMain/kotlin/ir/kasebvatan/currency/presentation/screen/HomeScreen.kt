package ir.kasebvatan.currency.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import ir.kasebvatan.currency.domain.model.CurrencyType
import ir.kasebvatan.currency.presentation.components.CurrencyPickerDialog
import ir.kasebvatan.currency.presentation.components.HomeBody
import ir.kasebvatan.currency.presentation.components.HomeHeader
import ui.theme.surfaceColor

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<HomeViewModel>()
        val rateStatus by viewModel.rateStatus
        val allCurrencies = viewModel.allCurrencies
        val sourceCurrency by viewModel.sourceCurrency
        val targetCurrency by viewModel.targetCurrency
        var amount by rememberSaveable { mutableStateOf(0.0) }

        var selectedCurrencyType: CurrencyType by remember {
            mutableStateOf(CurrencyType.None)
        }
        var dialogOpened by remember { mutableStateOf(false) }

        if (dialogOpened && selectedCurrencyType != CurrencyType.None) {
            CurrencyPickerDialog(
                currencies = allCurrencies,
                currencyType = selectedCurrencyType,
                onConfirmClicked = { currencyCode ->
                    if (selectedCurrencyType is CurrencyType.Source) {
                        viewModel.sendEvent(HomeUiEvent.saveSourceCurrencyCode(currencyCode.name))
                    } else if (selectedCurrencyType is CurrencyType.Target) {
                        viewModel.sendEvent(HomeUiEvent.saveTargetCurrencyCode(currencyCode.name))
                    }
                    dialogOpened = false

                },
                onDismiss = {
                    selectedCurrencyType = CurrencyType.None
                    dialogOpened = false
                }
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(surfaceColor)
        ) {
            HomeHeader(
                source = sourceCurrency,
                target = targetCurrency,
                status = rateStatus,
                amount = amount,
                onRatesRefresh = {
                    viewModel.sendEvent(HomeUiEvent.RefreshRates)
                },
                onSwitchClick = { viewModel.sendEvent(HomeUiEvent.SwitchCurrencies) },
                onAmountChanged = { amount = it },
                onCurrencyTypeClicked = { currencyType ->
                    selectedCurrencyType = currencyType
                    dialogOpened = true
                }
            )
            HomeBody(
                amount = amount,
                source = sourceCurrency,
                target = targetCurrency
            )
        }
    }
}