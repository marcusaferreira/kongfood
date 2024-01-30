package br.com.fiap.techchallenge.kongfood.frameworks.drivers.ui.dto

data class PaymentNotification(
    val orderID: String,
    val paymentID: String,
    val status: String
)
