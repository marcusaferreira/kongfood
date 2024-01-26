package br.com.fiap.techchallenge.kongfood.domain.customer

import br.com.fiap.techchallenge.kongfood.domain.customer.entities.CPF
import br.com.fiap.techchallenge.kongfood.domain.customer.entities.Customer
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerRequestModel
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerResponseModel
import java.util.UUID

internal class CustomerProvider {

    fun getCustomerToBeCreated(): CustomerRequestModel {
        return CustomerRequestModel(
            id = null,
            name = "John Doe",
            cpf = "48583291004",
            email = "customer@mail.com",
            phone = "11999999999"
        )
    }

    fun getCreatedCustomer(id: UUID, cpf: String): CustomerRequestModel {
        return CustomerRequestModel(
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

    fun getValidCustomerResponse(): CustomerResponseModel {
        return CustomerResponseModel(
            id = UUID.randomUUID().toString(),
            name = "John Doe",
            cpf = "48583291004",
            email = "customer@mail.com",
            phone = "11999999999"
        )
    }
}