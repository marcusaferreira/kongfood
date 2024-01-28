package br.com.fiap.techchallenge.kongfood.domain.order.usecases

import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderLineRequestModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel
import java.util.*

fun interface OrderRemoveProductBoundary {

    fun removeOrderLine(orderId: UUID, orderLine: OrderLineRequestModel) : OrderResponseModel

}
