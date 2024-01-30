package br.com.fiap.techchallenge.kongfood.domain.order.entities

enum class OrderStatus {
    CREATED,
    PENDING,
    ACCEPTED,
    FAILED,
    IN_PREPARATION,
    READY,
    FINISHED,
    CANCELED
}
