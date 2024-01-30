package br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.presenters

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse

class PaymentResponseFormatter : PaymentPresenter{
    override fun prepareSucessResponse(orderID: String, status: String): PaymentResponse {
        return PaymentResponse(orderID, status)
    }

    override fun prepareFailureResponse(error: String) {
        throw DomainException(error)
    }
}