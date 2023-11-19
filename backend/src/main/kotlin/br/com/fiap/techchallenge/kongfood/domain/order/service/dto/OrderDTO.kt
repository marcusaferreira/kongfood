package br.com.fiap.techchallenge.kongfood.domain.order.service.dto

import br.com.fiap.techchallenge.kongfood.domain.order.Order
import br.com.fiap.techchallenge.kongfood.domain.order.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class OrderDTO(
    val id: UUID,
    val lines: List<OrderLineDTO>,
    val status: OrderStatus,
    val total: BigDecimal,
    val clientId: UUID? = null,
    val initialDateTime: LocalDateTime = LocalDateTime.now(),
    val finishedDateTime: LocalDateTime? = null,
    val trackOrderCode: String? = null
) {

    companion object {
        fun from(order: Order): OrderDTO {
            return OrderDTO(
                order.id,
                order.lines.map {
                    OrderLineDTO(
                        it.product.id,
                        it.product.name,
                        it.product.description,
                        it.product.price,
                        it.product.category.type,
                        it.quantity,
                        it.note
                    )
                },
                order.status,
                order.total,
                order.customerId,
                order.initialDateTime,
                order.finishedDateTime,
                order.trackOrderCode
            )
        }
    }

}
