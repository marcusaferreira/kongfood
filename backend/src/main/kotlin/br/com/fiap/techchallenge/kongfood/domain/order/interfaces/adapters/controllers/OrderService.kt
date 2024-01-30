package br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.controllers

import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderLineRequestModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel
import java.util.*

interface OrderService {

    fun createOrder(customerId: UUID?): UUID

    fun addOrderLine(orderId: UUID, orderLine: OrderLineRequestModel)

    fun removeOrderLine(orderId: UUID, orderLine: OrderLineRequestModel)

    fun confirmOrder(orderId: UUID)

    fun prepareOrder(orderId: UUID)

    fun notifyPreparedOrder(orderId: UUID)

    fun cancelOrder(orderId: UUID)

    fun finishOrder(orderId: UUID)

    fun getOrderData(orderId: UUID): OrderResponseModel

    fun listOrdersOfTheDayByState(status: OrderStatus): List<OrderResponseModel>

    fun listPriorityOrdersOfTheDay(): List<OrderResponseModel>
    fun reject(orderID: UUID)

}