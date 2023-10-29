package br.com.fiap.techchallenge.kongfood.domain.customer

import java.util.UUID

class Customer(
    val id: UUID,
    val name: String,
    val email: String,
    val phone: String?,
    val cpf: CPF?,
    var isActive: Boolean = true,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}