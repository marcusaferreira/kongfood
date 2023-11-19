package br.com.fiap.techchallenge.kongfood.domain.order.service

import br.com.fiap.techchallenge.kongfood.domain.order.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.service.dto.OrderDTO
import br.com.fiap.techchallenge.kongfood.domain.order.service.dto.OrderLineDTO
import java.util.UUID

interface OrderService {

    fun createOrder(customerId: UUID?): UUID

    fun addOrderLine(orderId: UUID, orderLine: OrderLineDTO)

    fun removeOrderLine(orderId: UUID, orderLine: OrderLineDTO)

    fun confirmOrder(orderId: UUID)

    fun prepareOrder(orderId: UUID)

    fun notifyPreparedOrder(orderId: UUID)

    fun cancelOrder(orderId: UUID)

    fun finishOrder(orderId: UUID)

    fun getOrderData(orderId: UUID): OrderDTO

    fun listOrdersOfTheDayByState(status: OrderStatus): List<OrderDTO>

}