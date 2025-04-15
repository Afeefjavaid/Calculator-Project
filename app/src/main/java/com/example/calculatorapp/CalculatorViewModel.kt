package com.example.calculatorapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Delete -> performDeletion()
            is CalculatorAction.Decimal -> enterDecimal()
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null && state.number1.length < MAX_NUM_LENGTH) {
            updateState(state.number1 + number, state.number2, state.operation)
        } else if (state.number2.length < MAX_NUM_LENGTH) {
            updateState(state.number1, state.number2 + number, state.operation)
        }
    }

    private fun enterDecimal() {
        if (state.operation == null && !state.number1.contains(".") && state.number1.isNotBlank()) {
            updateState(state.number1 + ".", state.number2, state.operation)
        } else if (!state.number2.contains(".") && state.number2.isNotBlank()) {
            updateState(state.number1, state.number2 + ".", state.operation)
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    private fun performDeletion() {
        when {
            state.number2.isNotBlank() -> updateState(state.number1, state.number2.dropLast(1), state.operation)
            state.operation != null -> updateState(state.number1, state.number2, null)
            state.number1.isNotBlank() -> updateState(state.number1.dropLast(1), state.number2, state.operation)
        }
    }

    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()

        if (number1 != null && number2 != null && state.operation != null) {
            val result = when (state.operation) {
                is CalculatorOperation.Add -> number1 + number2
                is CalculatorOperation.Subtract -> number1 - number2
                is CalculatorOperation.Multiply -> number1 * number2
                is CalculatorOperation.Divide -> if (number2 == 0.0) Double.NaN else number1 / number2
                else -> return
            }

            state = state.copy(number1 = result.toString().take(15), number2 = "", operation = null)
        }
    }
    companion object {
        private const val MAX_NUM_LENGTH = 8
    }

    private fun updateState(number1: String, number2: String, operation: CalculatorOperation?) {
        state = state.copy(number1 = number1, number2 = number2, operation = operation)
    }
}