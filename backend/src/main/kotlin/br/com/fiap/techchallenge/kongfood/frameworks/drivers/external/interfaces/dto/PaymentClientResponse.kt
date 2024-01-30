package br.com.fiap.techchallenge.kongfood.frameworks.drivers.external.interfaces.dto

data class PaymentClientResponse(
    val orderID: String?,
    val amount: Double?,
    val customerID: String?,
    val paymentID: String,
    val status: String
)
