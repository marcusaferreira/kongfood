package br.com.fiap.techchallenge.kongfood.domain.order.usecases

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.order.entities.Order
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderPresenter
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.repository.OrderRepository
import java.util.*

class OrderSearchInteractor(
    val orderRepository: OrderRepository,
    val orderPresenter: OrderPresenter
) : OrderSearchBoundary {
    override fun getOrder(orderId: UUID): Order {
        return orderRepository.findById(orderId)
            .orElseThrow { throw DomainException("Order not found") }
    }

    override fun getOrderData(orderId: UUID): OrderResponseModel {
        val order = orderRepository.findById(orderId)
        if (order.isEmpty) {
            orderPresenter.prepareFailureResponse("Order not found")
        }
        return orderPresenter.prepareSuccessResponse(order.get())
    }

    override fun listOrdersOfTheDayByState(status: OrderStatus): List<OrderResponseModel> {
        val orders = orderRepository.findOrdersOfTheDayByStatus(status)
        return orders.map { orderPresenter.prepareSuccessResponse(it) }
    }

    override fun listPriorityOrdersOfTheDay(): List<OrderResponseModel> {
        val orders = mutableListOf<OrderResponseModel>()
        orders.addAll(listOrdersOfTheDayByState(OrderStatus.READY))
        orders.addAll(listOrdersOfTheDayByState(OrderStatus.IN_PREPARATION))
        orders.addAll(listOrdersOfTheDayByState(OrderStatus.ACCEPTED))

        if (orders.isEmpty()) {
            orderPresenter.prepareFailureResponse("No orders found")
        }
        return orders
    }
}