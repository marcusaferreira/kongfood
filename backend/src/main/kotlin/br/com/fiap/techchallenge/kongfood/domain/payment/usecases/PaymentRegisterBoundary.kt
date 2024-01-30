package br.com.fiap.techchallenge.kongfood.domain.payment.usecases

import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse
fun interface PaymentRegisterBoundary {

    fun register(orderID: String): PaymentResponse
}