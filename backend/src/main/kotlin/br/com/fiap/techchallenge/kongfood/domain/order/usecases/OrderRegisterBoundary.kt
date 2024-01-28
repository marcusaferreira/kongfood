package br.com.fiap.techchallenge.kongfood.domain.order.usecases

import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel
import java.util.*

fun interface OrderRegisterBoundary {

    fun create(customerId: UUID?): OrderResponseModel
}