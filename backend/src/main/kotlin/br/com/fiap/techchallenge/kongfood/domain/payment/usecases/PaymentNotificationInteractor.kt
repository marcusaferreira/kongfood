package br.com.fiap.techchallenge.kongfood.domain.payment.usecases

import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.controllers.OrderService
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.presenters.PaymentPresenter
import java.util.*

class PaymentNotificationInteractor(
    val orderService: OrderService,
    val paymentPresenter: PaymentPresenter
) : PaymentNotificationBoundary {

    override fun notify(orderID: String, paymentResult: String): PaymentResponse? {
        return when (paymentResult) {
            "APPROVED" -> {
                orderService.prepareOrder(UUID.fromString(orderID))
                paymentPresenter.prepareSucessResponse(orderID, "APPROVED")

            }
            "REJECTED" -> {
                orderService.reject(UUID.fromString(orderID))
                paymentPresenter.prepareSucessResponse(orderID, "REJECTED")
            }
            else -> {
                paymentPresenter.prepareFailureResponse("Invalid payment status")
                null
            }
        }
    }
}