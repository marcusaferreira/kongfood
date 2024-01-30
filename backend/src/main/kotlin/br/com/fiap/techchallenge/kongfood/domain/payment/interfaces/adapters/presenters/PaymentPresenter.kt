package br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.presenters

import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse

interface PaymentPresenter {
    fun prepareSucessResponse(orderID: String, status: String): PaymentResponse
    fun prepareFailureResponse(error: String)
}