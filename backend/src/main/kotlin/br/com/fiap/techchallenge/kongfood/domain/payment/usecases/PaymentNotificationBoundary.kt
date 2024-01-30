package br.com.fiap.techchallenge.kongfood.domain.payment.usecases

import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse
fun interface PaymentNotificationBoundary {

    fun notify(orderID: String, paymentResult: String): PaymentResponse?
}