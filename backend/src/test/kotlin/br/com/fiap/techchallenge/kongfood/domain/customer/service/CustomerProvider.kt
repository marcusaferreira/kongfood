package br.com.fiap.techchallenge.kongfood.domain.customer.service

import br.com.fiap.techchallenge.kongfood.domain.customer.CPF
import br.com.fiap.techchallenge.kongfood.domain.customer.Customer
import br.com.fiap.techchallenge.kongfood.domain.customer.service.dto.CustomerDTO
import java.util.UUID

internal class CustomerProvider {

    fun getCustomerToBeCreated(): CustomerDTO {
        return CustomerDTO(
            id = null,
            name = "John Doe",
            cpf = "48583291004",
            email = "customer@mail.com",
            phone = "11999999999"
        )
    }

    fun getCreatedCustomer(id: UUID, cpf: String): CustomerDTO {
        return CustomerDTO(
            id = id,
            name = "John Doe",
            cpf = cpf,
            email = "customer@mail.com",
            phone = "11999999999"
        )
    }

    fun getValidCustomer(): Customer {
        return Customer(
            id = UUID.randomUUID(),
            name = "John Doe",
            cpf = CPF("48583291004"),
            email = "customer@mail.com",
            phone = "11999999999"
        )
    }
}