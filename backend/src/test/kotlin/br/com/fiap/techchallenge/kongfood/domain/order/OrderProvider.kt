package br.com.fiap.techchallenge.kongfood.domain.order

import java.math.BigDecimal
import java.util.*

class OrderProvider {

    fun getCreatedOrder(clientId: UUID?): Order {
        return Order(UUID.randomUUID(), mutableListOf(), OrderStatus.CREATED, BigDecimal.ZERO, clientId)
    }

    fun getCreatedCompletedOrder(clientId: UUID?): Order {
        return Order(UUID.randomUUID(), mutableListOf(), OrderStatus.FINISHED, BigDecimal.ZERO, clientId)
    }

    fun getCreatedOrderWithSpecificState(clientId: UUID?, orderStatus: OrderStatus): Order {
        return Order(UUID.randomUUID(), mutableListOf(), orderStatus, BigDecimal.ZERO, clientId)
    }
}