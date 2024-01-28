package br.com.fiap.techchallenge.kongfood.domain.order.usecases

import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel
import java.util.*

interface OrderChangeStateBoundary {

    fun changeState(orderId: UUID, status: OrderStatus): OrderResponseModel
    fun cancelOrder(orderId: UUID): OrderResponseModel
    fun finishOrder(orderId: UUID): OrderResponseModel
}
