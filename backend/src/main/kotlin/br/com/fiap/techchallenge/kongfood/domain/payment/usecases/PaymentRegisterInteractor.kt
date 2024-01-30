package br.com.fiap.techchallenge.kongfood.domain.payment.usecases

import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.controllers.OrderService
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.presenters.PaymentPresenter
import java.util.*

class PaymentRegisterInteractor(
    val orderService: OrderService,
    val paymentPresenter: PaymentPresenter
) : PaymentRegisterBoundary{

    override fun register(orderID: String): PaymentResponse{
        val randoBoolean = Random().nextBoolean()
        return if (randoBoolean) {
            orderService.prepareOrder(UUID.fromString(orderID))
            paymentPresenter.prepareSucessResponse(orderID, "APPROVED")
        }else{
            orderService.reject(UUID.fromString(orderID))
            paymentPresenter.prepareSucessResponse(orderID, "APPROVED")
        }
    }
}