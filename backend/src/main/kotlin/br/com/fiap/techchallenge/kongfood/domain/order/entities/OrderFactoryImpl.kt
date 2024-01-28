package br.com.fiap.techchallenge.kongfood.domain.order.entities

import java.math.BigDecimal
import java.util.*

class OrderFactoryImpl : OrderFactory {
    override fun createOrder(customerId: UUID?): Order {
        return Order(UUID.randomUUID(), mutableListOf(), OrderStatus.CREATED, BigDecimal.ZERO, customerId)
    }
}