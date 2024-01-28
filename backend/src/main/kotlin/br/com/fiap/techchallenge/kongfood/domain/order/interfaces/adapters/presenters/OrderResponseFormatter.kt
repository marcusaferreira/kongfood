package br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.order.entities.Order
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderLineResponseModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel

class OrderResponseFormatter: OrderPresenter {

    override fun prepareSuccessResponse(order: Order): OrderResponseModel {
        return OrderResponseModel(
            order.id,
            order.lines.map {
                OrderLineResponseModel(
                    it.product.id,
                    it.product.name,
                    it.product.description,
                    it.product.price,
                    it.product.category.type,
                    it.quantity,
                    it.note
                )
            },
            order.status.toString(),
            order.total,
            order.customerId,
            order.initialDateTime,
            order.finishedDateTime,
            order.trackOrderCode
        )
    }

    override fun prepareFailureResponse(error: String) {
        throw DomainException(error)
    }
}