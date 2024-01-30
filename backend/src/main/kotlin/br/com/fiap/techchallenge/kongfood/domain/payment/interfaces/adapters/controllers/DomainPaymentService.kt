package br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.controllers

import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.controllers.OrderService
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse
import br.com.fiap.techchallenge.kongfood.domain.payment.usecases.PaymentNotificationBoundary
import br.com.fiap.techchallenge.kongfood.domain.payment.usecases.PaymentRegisterBoundary
import br.com.fiap.techchallenge.kongfood.domain.payment.usecases.PaymentVerificationBoundary
import br.com.fiap.techchallenge.kongfood.frameworks.drivers.ui.dto.PaymentNotification
import java.util.*

class DomainPaymentService(
    val paymentNotificationBoundary: PaymentNotificationBoundary,
    val paymentRegisterBoundary: PaymentRegisterBoundary,
    val paymentVerificationBoundary: PaymentVerificationBoundary,
): PaymentService{
    override fun notify(orderID: String, payment: PaymentNotification) {
        paymentNotificationBoundary.notify(orderID, payment.status)
    }

    override fun request(orderID: String): PaymentResponse {
        return paymentRegisterBoundary.register(orderID)
        TODO("Implement the communication with the payment gateway")
    }

    override fun verify(orderID: String): PaymentResponse {
        return paymentVerificationBoundary.verify(orderID)
        TODO("Implement the communication with the payment gateway")
    }
}