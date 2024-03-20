package br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.controllers

import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse
import br.com.fiap.techchallenge.kongfood.domain.payment.usecases.PaymentNotificationBoundary
import br.com.fiap.techchallenge.kongfood.domain.payment.usecases.PaymentRegisterBoundary
import br.com.fiap.techchallenge.kongfood.domain.payment.usecases.PaymentVerificationBoundary
import br.com.fiap.techchallenge.kongfood.frameworks.drivers.ui.dto.PaymentNotification

class DomainPaymentService(
    val paymentNotificationBoundary: PaymentNotificationBoundary,
    val paymentRegisterBoundary: PaymentRegisterBoundary,
    val paymentVerificationBoundary: PaymentVerificationBoundary,
): PaymentService{
    override fun notify(orderID: String, payment: PaymentNotification) {
        paymentNotificationBoundary.notify(orderID, payment.status)
    }

    override fun request(orderID: String): PaymentResponse {
        TODO("Implement the communication with the payment gateway")
        return paymentRegisterBoundary.register(orderID)
    }

    override fun verify(orderID: String): PaymentResponse {
        TODO("Implement the communication with the payment gateway")
        return paymentVerificationBoundary.verify(orderID)
    }
}