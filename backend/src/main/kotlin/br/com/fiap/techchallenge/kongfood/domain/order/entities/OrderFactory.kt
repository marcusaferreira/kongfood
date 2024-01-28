package br.com.fiap.techchallenge.kongfood.domain.order.entities

import java.util.UUID

fun interface OrderFactory {

    fun createOrder(customerId: UUID?): Order
}
