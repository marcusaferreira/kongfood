package br.com.fiap.techchallenge.kongfood.domain.payment.usecases

import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.controllers.OrderService
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.presenters.PaymentPresenter
import java.util.*

class PaymentVerificationInteractor(
    val orderService: OrderService,
    val paymentPresenter: PaymentPresenter
) : PaymentVerificationBoundary{

    override fun verify(orderID: String): PaymentResponse{
        val order = orderService.getOrderData(UUID.fromString(orderID))
        when(order.status) {
            "CREATED" ,"PENDING" ,"ACCEPTED" -> {
                return paymentPresenter.prepareSucessResponse(
                    orderID = orderID,
                    status = "PENDING"
                )
            }
            "IN_PREPARATION", "READY", "FINISHED" -> {
                return paymentPresenter.prepareSucessResponse(
                    orderID = orderID,
                    status = "PAID"
                )
            }
            "CANCELED" -> {
                return paymentPresenter.prepareSucessResponse(
                    orderID = orderID,
                    status = "CANCELED"
                )
            }
            "FAILED" -> {
                return paymentPresenter.prepareSucessResponse(
                    orderID = orderID,
                    status = "FAILED"
                )
            }
            else -> return paymentPresenter.prepareSucessResponse(
                orderID = orderID,
                status = "UNKNOWN"
            )
        }
    }
}