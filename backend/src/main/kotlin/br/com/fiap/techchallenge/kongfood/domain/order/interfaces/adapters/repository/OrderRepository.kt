package br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.repository

import br.com.fiap.techchallenge.kongfood.domain.order.entities.Order
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import java.util.Optional
import java.util.UUID

interface OrderRepository {

    fun findById(id: UUID): Optional<Order>
    fun findAll(): List<Order>
    fun save(order: Order)
    fun countOrdersOfTheDay(): Int
    fun findOrdersOfTheDayByStatus(status: OrderStatus): List<Order>
}