package br.com.fiap.techchallenge.kongfood.domain.order.usecases

import br.com.fiap.techchallenge.kongfood.domain.order.entities.Order
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel
import java.util.*

interface OrderSearchBoundary {

    /**
     * Get order by id and return Order
     * This function must be used only for internal use for the domain, to return order data to external use, use getOrderData
     * @param orderId
     * @return Order
     */
    fun getOrder(orderId: UUID): Order

    fun getOrderData(orderId: UUID): OrderResponseModel

    fun listOrdersOfTheDayByState(status: OrderStatus): List<OrderResponseModel>

    fun listPriorityOrdersOfTheDay(): List<OrderResponseModel>
}