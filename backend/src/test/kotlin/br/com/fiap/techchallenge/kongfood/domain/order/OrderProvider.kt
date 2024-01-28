package br.com.fiap.techchallenge.kongfood.domain.order

import br.com.fiap.techchallenge.kongfood.domain.order.entities.Order
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderResponseFormatter
import java.math.BigDecimal
import java.util.*

internal class OrderProvider {

    fun getCreatedOrder(clientId: UUID?): Order {
        return Order(UUID.randomUUID(), mutableListOf(), OrderStatus.CREATED, BigDecimal.ZERO, clientId)
    }

    fun getCreatedCompletedOrder(clientId: UUID?): Order {
        return Order(UUID.randomUUID(), mutableListOf(), OrderStatus.FINISHED, BigDecimal.ZERO, clientId)
    }

    fun getCreatedOrderWithSpecificState(clientId: UUID?, orderStatus: OrderStatus): Order {
        return Order(UUID.randomUUID(), mutableListOf(), orderStatus, BigDecimal.ZERO, clientId)
    }

    fun getCreatedOrder(orderId: UUID, clientId: UUID?): Order {
        return Order(orderId, mutableListOf(), OrderStatus.CREATED, BigDecimal.ZERO, clientId)
    }

    fun getCreatedOrderResponse(randomUUID: UUID): OrderResponseModel {
        val orderResponseFormatter = OrderResponseFormatter()
        return orderResponseFormatter.prepareSuccessResponse(getCreatedOrder(randomUUID, randomUUID))
    }
}