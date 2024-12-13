package ir.kasebvatan.currency

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ir.kasebvatan.currency.di.initializeKoin
import ir.kasebvatan.currency.presentation.screen.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    //val colors = if (isSystemInDarkTheme()) DarkColors else LightColors
    initializeKoin()
    MaterialTheme {
        Navigator(HomeScreen())
    }
}