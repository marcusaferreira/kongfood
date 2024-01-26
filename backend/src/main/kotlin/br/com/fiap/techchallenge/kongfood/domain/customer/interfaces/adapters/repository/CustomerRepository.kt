package br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.repository

import br.com.fiap.techchallenge.kongfood.domain.customer.entities.Customer
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerRequestModel
import java.util.*

interface CustomerRepository {

    fun save(customer: Customer)

    fun findCustomerByCpf(cpf: String): Optional<Customer>

    fun findCustomerById(id: UUID): Optional<Customer>
    fun findCustomerByEmail(email: String): Optional<Customer>
    fun verifyIfCustomerByEmailAlreadyExists(customerRequestModel: CustomerRequestModel): Boolean
}