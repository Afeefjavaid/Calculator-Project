package com.example.calculatorapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Calculator(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 8.dp,
    onAction: (CalculatorAction) -> Unit
) {
    val buttonRows: List<List<Pair<String, CalculatorAction>>> = listOf(
        listOf("AC" to CalculatorAction.Clear, "Del" to CalculatorAction.Delete, "/" to CalculatorAction.Operation(CalculatorOperation.Divide)),
        listOf("7" to CalculatorAction.Number(7), "8" to CalculatorAction.Number(8), "9" to CalculatorAction.Number(9), "x" to CalculatorAction.Operation(CalculatorOperation.Multiply)),
        listOf("4" to CalculatorAction.Number(4), "5" to CalculatorAction.Number(5), "6" to CalculatorAction.Number(6), "-" to CalculatorAction.Operation(CalculatorOperation.Subtract)),
        listOf("1" to CalculatorAction.Number(1), "2" to CalculatorAction.Number(2), "3" to CalculatorAction.Number(3), "+" to CalculatorAction.Operation(CalculatorOperation.Add)),
        listOf("0" to CalculatorAction.Number(0), "." to CalculatorAction.Decimal, "=" to CalculatorAction.Calculate)
    )

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            DisplayText(state)

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                items(buttonRows.size) { index ->
                    ButtonRow(buttons = buttonRows[index], buttonSpacing = buttonSpacing, onAction = onAction)
                }
            }
        }
    }
}

@Composable
fun DisplayText(state: CalculatorState, modifier: Modifier = Modifier) {
    Text(
        text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
        textAlign = TextAlign.End,
        modifier = modifier.fillMaxWidth().padding(vertical = 32.dp),
        fontWeight = FontWeight.Light,
        fontSize = 80.sp,
        color = Color.White,
        softWrap = false
    )
}

@Composable
fun ButtonRow(
    buttons: List<Pair<String, CalculatorAction>>,
    buttonSpacing: Dp,
    onAction: (CalculatorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        buttons.forEach { (symbol, action) ->
            CalculatorButton(
                symbols = symbol,
                modifier = Modifier
                    .background(if (symbol in listOf("+", "-", "x", "/")) Color.Yellow else Color.DarkGray)
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = { onAction(action) }
            )
        }
    }
}