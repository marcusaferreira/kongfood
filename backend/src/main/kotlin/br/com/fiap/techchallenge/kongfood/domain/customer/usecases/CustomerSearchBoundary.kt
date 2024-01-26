package br.com.fiap.techchallenge.kongfood.domain.customer.usecases

import br.com.fiap.techchallenge.kongfood.domain.customer.entities.Customer
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerRequestModel
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerResponseModel
import java.util.*

interface CustomerSearchBoundary {

    fun findCustomerByCpf(cpf: String): CustomerResponseModel

    fun findCustomerById(id: String): CustomerResponseModel

    fun findCustomerByEmail(email: String): CustomerResponseModel
    fun exisits(customer: Optional<Customer>, customerID: UUID)
    fun verifyIfCustomerByEmailAlreadyExists(customerRequestModel: CustomerRequestModel)
}