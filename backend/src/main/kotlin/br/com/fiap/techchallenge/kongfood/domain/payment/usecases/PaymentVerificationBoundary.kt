package br.com.fiap.techchallenge.kongfood.domain.payment.usecases

import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse
fun interface PaymentVerificationBoundary {

    fun verify(orderID: String): PaymentResponse
}