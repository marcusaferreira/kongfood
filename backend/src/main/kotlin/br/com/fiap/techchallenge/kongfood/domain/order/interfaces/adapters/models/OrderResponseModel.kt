package br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class OrderResponseModel(
    val id: UUID,
    val lines: List<OrderLineResponseModel>,
    val status: String,
    val total: BigDecimal,
    val clientId: UUID? = null,
    val initialDateTime: LocalDateTime = LocalDateTime.now(),
    val finishedDateTime: LocalDateTime? = null,
    val trackOrderCode: String? = null
)
