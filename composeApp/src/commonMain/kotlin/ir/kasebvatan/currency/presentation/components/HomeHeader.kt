package ir.kasebvatan.currency.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import currencyapp.composeapp.generated.resources.Res
import currencyapp.composeapp.generated.resources.exchange_illustration
import currencyapp.composeapp.generated.resources.refresh_ic
import currencyapp.composeapp.generated.resources.switch_ic
import ir.kasebvatan.currency.domain.model.Currency
import ir.kasebvatan.currency.domain.model.CurrencyCode
import ir.kasebvatan.currency.domain.model.CurrencyType
import ir.kasebvatan.currency.domain.model.DisplayResult
import ir.kasebvatan.currency.domain.model.RateStatus
import ir.kasebvatan.currency.domain.model.RequestState
import ir.kasebvatan.currency.util.displayCurrentDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.theme.headerColor
import ui.theme.staleColor

@Composable
fun HomeHeader(
    status: RateStatus,
    onRatesRefresh: () -> Unit,
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    onSwitchClick: () -> Unit,
    amount: Double,
    onAmountChanged: (Double) -> Unit,
    onCurrencyTypeClicked: (CurrencyType) -> Unit

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp))
            .background(headerColor)
            .padding(top = 10.dp)
            .padding(all = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        RatesStatus(status, onRatesRefresh)
        Spacer(modifier = Modifier.height(24.dp))
        CurrencyInput(
            source = source,
            target = target,
            onSwitchClick = onSwitchClick,
            onCurrencyTypeClicked = onCurrencyTypeClicked
        )
        Spacer(modifier = Modifier.height(24.dp))
        AmountInput(
            amount = amount,
            onAmountChanged = onAmountChanged
        )
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RatesStatus(
    status: RateStatus,
    onRatesRefresh: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row {
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(Res.drawable.exchange_illustration),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = displayCurrentDateTime(),
                    color = Color.White
                )
                Text(
                    text = status.title,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    color = status.color
                )
            }
        }

        if (status == RateStatus.Stale) {
            IconButton(onClick = onRatesRefresh) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.refresh_ic),
                    contentDescription = "",
                    tint = staleColor
                )
            }
        }

    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CurrencyInput(
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    onSwitchClick: () -> Unit,
    onCurrencyTypeClicked: (CurrencyType) -> Unit
) {

    var animationStarted by remember { mutableStateOf(false) }
    val animatedRotation by animateFloatAsState(
        targetValue = if (animationStarted) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )



    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyView(
            placeHolder = "From",
            currency = source,
            onClick = {
                if (source.isSuccess()) {
                    onCurrencyTypeClicked(
                        CurrencyType.Source(
                            currencyCode = CurrencyCode.valueOf(
                                source.getSuccessData().code
                            )
                        )
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(14.dp))

        IconButton(
            modifier = Modifier
                .padding(top = 24.dp)
                .graphicsLayer { rotationY = animatedRotation },
            onClick = {
                animationStarted = animationStarted.not()
                onSwitchClick()
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.switch_ic),
                contentDescription = "",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        CurrencyView(
            placeHolder = "To",
            currency = target,
            onClick = {
                if (target.isSuccess()) {
                    onCurrencyTypeClicked(
                        CurrencyType.Target(
                            currencyCode = CurrencyCode.valueOf(
                                target.getSuccessData().code
                            )
                        )
                    )
                }
            }
        )

    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RowScope.CurrencyView(
    placeHolder: String,
    currency: RequestState<Currency>,
    onClick: () -> Unit
) {

    Column(modifier = Modifier.weight(1f)) {

        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = placeHolder,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(size = 8.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .height(54.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            currency.DisplayResult(
                onSuccess = { data ->
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(
                            CurrencyCode.valueOf(data.code).flag
                        ),
                        tint = Color.Unspecified,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = CurrencyCode.valueOf(data.code).name,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = Color.White
                    )
                }
            )
        }


    }


}

@Composable
fun AmountInput(
    amount: Double,
    onAmountChanged: (Double) -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 8.dp))
            .animateContentSize()
            .height(54.dp),
        value = "$amount",
        onValueChange = {
            onAmountChanged(it.toDouble())
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White.copy(alpha = 0.05f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
            disabledContainerColor = Color.White.copy(alpha = 0.05f),
            errorContainerColor = Color.White.copy(alpha = 0.05f),
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White
        ),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )
}
