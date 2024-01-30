package br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.controllers

import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse
import br.com.fiap.techchallenge.kongfood.frameworks.drivers.ui.dto.PaymentNotification

interface PaymentService {
    fun notify(orderID: String, payment: PaymentNotification)
    fun request(orderID: String): PaymentResponse
    fun verify(orderID: String): PaymentResponse
}