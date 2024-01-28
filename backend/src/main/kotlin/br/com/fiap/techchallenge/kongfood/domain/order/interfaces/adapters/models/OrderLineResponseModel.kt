package br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models

import java.math.BigDecimal
import java.util.*

data class OrderLineResponseModel(
    val productId: UUID,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val category: String,
    val quantity: Int,
    val note: String? = null
)