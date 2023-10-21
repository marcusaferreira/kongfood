package br.com.fiap.techchallenge.kongfood.domain.order.service

import br.com.fiap.techchallenge.kongfood.domain.order.Order
import br.com.fiap.techchallenge.kongfood.domain.order.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.Product
import br.com.fiap.techchallenge.kongfood.domain.order.repository.OrderRepository
import java.math.BigDecimal
import java.util.*

class DomainOrderService(
    val orderRepository: OrderRepository
) : OrderService {

    override fun createOrder(clientId: UUID?): UUID {
        val order = Order(UUID.randomUUID(), mutableListOf(), OrderStatus.CREATED, BigDecimal.ZERO, clientId)
        val lastOrderNumber = orderRepository.countOrdersOfTheDay()
        order.generateTrackOrderCode(lastOrderNumber)
        orderRepository.save(order)

        return order.id
    }

    override fun addOrderLine(orderId: UUID, product: Product, quantity: Int) {
        val order = getOrder(orderId)
        order.addOrderLine(product, quantity)
        order.chageState(OrderStatus.PENDING)

        orderRepository.save(order)
    }

    override fun removeOrderLine(orderId: UUID, product: Product) {
        val order = getOrder(orderId)
        order.removeOrderLine(product)
        if (order.lines.isEmpty()) {
            order.chageState(OrderStatus.CREATED)
        } else {
            order.chageState(OrderStatus.PENDING)
        }

        orderRepository.save(order)
    }

    override fun confirmOrder(orderId: UUID) {
        val order = getOrder(orderId)
        order.chageState(OrderStatus.ACCEPTED)

        orderRepository.save(order)
    }

    override fun prepareOrder(orderId: UUID) {
        val order = getOrder(orderId)
        order.chageState(OrderStatus.IN_PREPARATION)

        orderRepository.save(order)
    }

    override fun notifyPreparedOrder(orderId: UUID) {
        val order = getOrder(orderId)
        order.chageState(OrderStatus.READY)

        orderRepository.save(order)
    }

    override fun cancelOrder(orderId: UUID) {
        val order = getOrder(orderId)
        order.cancel()

        orderRepository.save(order)
    }

    override fun finishOrder(orderId: UUID) {
        val order = getOrder(orderId)
        order.complete()

        orderRepository.save(order)
    }

    override fun getOrderData(orderId: UUID): OrderDTO {
        TODO("Not yet implemented")
    }

    override fun listOrders(): List<OrderDTO> {
        TODO("Not yet implemented")
    }

    private fun getOrder(orderId: UUID): Order {
        return orderRepository.findById(orderId)
            .orElseThrow { throw RuntimeException("Order not found") }
    }
}