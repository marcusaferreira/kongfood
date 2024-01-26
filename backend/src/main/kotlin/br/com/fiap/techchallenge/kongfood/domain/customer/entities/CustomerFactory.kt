package br.com.fiap.techchallenge.kongfood.domain.customer.entities

fun interface CustomerFactory {

    fun create(
        id: String?, name: String,
        email: String,
        phone: String?,
        cpf: String?,
        isActive: Boolean
    ): Customer
}