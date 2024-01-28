package br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters

import br.com.fiap.techchallenge.kongfood.domain.order.entities.Order
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel

interface OrderPresenter {

    fun prepareSuccessResponse(order: Order): OrderResponseModel

    fun prepareFailureResponse(error: String)
}