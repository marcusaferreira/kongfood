package br.com.fiap.techchallenge.kongfood.domain.order.service

import br.com.fiap.techchallenge.kongfood.domain.order.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.Product
import br.com.fiap.techchallenge.kongfood.domain.order.service.dto.OrderDTO
import java.util.UUID

interface OrderService {

    fun createOrder(clientId: UUID?): UUID

    fun addOrderLine(orderId: UUID, product: Product, quantity: Int)

    fun removeOrderLine(orderId: UUID, product: Product)

    fun confirmOrder(orderId: UUID)

    fun prepareOrder(orderId: UUID)

    fun notifyPreparedOrder(orderId: UUID)

    fun cancelOrder(orderId: UUID)

    fun finishOrder(orderId: UUID)

    fun getOrderData(orderId: UUID): OrderDTO

    fun listOrdersOfTheDayByState(status: OrderStatus): List<OrderDTO>

}