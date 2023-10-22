package br.com.fiap.techchallenge.kongfood.domain.order.service.dto

import java.util.UUID

data class OrderLineDTO(val productId: UUID, val name: String, val description: String, val quantity: Int) {

}
