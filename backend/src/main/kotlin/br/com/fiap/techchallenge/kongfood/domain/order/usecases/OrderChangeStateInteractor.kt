package br.com.fiap.techchallenge.kongfood.domain.order.usecases

import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderPresenter
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.repository.OrderRepository
import java.util.UUID

class OrderChangeStateInteractor(
    val orderRepository: OrderRepository,
    val orderPresenter: OrderPresenter,
    val orderSearchBoundary: OrderSearchBoundary
) : OrderChangeStateBoundary {

    override fun changeState(orderId: UUID, status: OrderStatus): OrderResponseModel {
        val order = orderSearchBoundary.getOrder(orderId)
        order.chageState(status)

        orderRepository.save(order)
        return orderPresenter.prepareSuccessResponse(order)
    }

    override fun cancelOrder(orderId: UUID): OrderResponseModel {
        val order = orderSearchBoundary.getOrder(orderId)
        order.cancel()

        orderRepository.save(order)
        return orderPresenter.prepareSuccessResponse(order)
    }

    override fun finishOrder(orderId: UUID): OrderResponseModel {
        val order = orderSearchBoundary.getOrder(orderId)
        order.complete()

        orderRepository.save(order)
        return orderPresenter.prepareSuccessResponse(order)
    }

}