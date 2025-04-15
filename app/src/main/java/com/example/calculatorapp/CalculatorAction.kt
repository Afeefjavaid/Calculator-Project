package com.example.calculatorapp

sealed class CalculatorAction{
    data class Number(val number: Int):CalculatorAction()
    data object Clear:CalculatorAction()
    data object Decimal:CalculatorAction()
    data object Delete:CalculatorAction()
    data object Calculate:CalculatorAction()
    data class Operation(val operation:CalculatorOperation):CalculatorAction()
}