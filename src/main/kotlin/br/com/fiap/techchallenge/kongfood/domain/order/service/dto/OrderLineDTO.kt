package br.com.fiap.techchallenge.kongfood.domain.order.service.dto

import java.math.BigDecimal
import java.util.UUID

data class OrderLineDTO(
    val productId: UUID,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val quantity: Int
) {

}
