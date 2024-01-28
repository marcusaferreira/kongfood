package br.com.fiap.techchallenge.kongfood.domain.order.entities

import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderLineRequestModel

fun interface OrderProductFactory {
    fun createOrderProduct(orderLine: OrderLineRequestModel): Product

}
