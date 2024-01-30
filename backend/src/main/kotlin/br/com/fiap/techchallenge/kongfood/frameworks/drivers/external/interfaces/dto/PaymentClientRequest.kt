package br.com.fiap.techchallenge.kongfood.frameworks.drivers.external.interfaces.dto

data class PaymentClientRequest(
    val orderID: String,
    val amount: Double,
    val notificarionURL: String = "http://localhost:8080/api/payments/notify/${orderID}",
    val customerID: String
)
