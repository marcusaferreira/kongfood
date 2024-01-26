package br.com.fiap.techchallenge.kongfood.domain.customer.entities

import java.util.UUID

class CustomerFactoryImplementation: CustomerFactory {
    override fun create(
        id: String?,
        name: String,
        email: String,
        phone: String?,
        cpf: String?,
        isActive: Boolean
    ): Customer {
        return Customer(
            id = if (id != null) UUID.fromString(id) else UUID.randomUUID(),
            name = name,
            email = email,
            phone = phone,
            cpf = if (cpf != null) CPF(cpf) else null,
            isActive = isActive)
    }
}