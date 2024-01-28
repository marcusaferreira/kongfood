package br.com.fiap.techchallenge.kongfood.domain.order.usecases

import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderProductFactory
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderLineRequestModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderPresenter
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.repository.OrderRepository
import java.util.*

class OrderAddProductInteractor(
    val orderRepository: OrderRepository,
    val orderPresenter: OrderPresenter,
    val orderSearchBoundary: OrderSearchBoundary,
    val orderProductFactory: OrderProductFactory
): OrderAddProductBoundary {

    override fun addOrderLine(orderId: UUID, orderLine: OrderLineRequestModel) : OrderResponseModel{
        val product = orderProductFactory.createOrderProduct(orderLine)
        val order = orderSearchBoundary.getOrder(orderId)

        order.addOrderLine(product, orderLine.quantity, orderLine.note)
        order.chageState(OrderStatus.PENDING)

        orderRepository.save(order)

        return orderPresenter.prepareSuccessResponse(order)
    }

}
