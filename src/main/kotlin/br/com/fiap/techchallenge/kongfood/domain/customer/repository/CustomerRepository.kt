package br.com.fiap.techchallenge.kongfood.domain.customer.repository

import br.com.fiap.techchallenge.kongfood.domain.customer.Customer
import java.util.*

interface CustomerRepository {

    fun save(customer: Customer)

    fun findCustomerByCpf(cpf: String): Optional<Customer>

    fun findCustomerById(id: UUID): Optional<Customer>
    fun findCustomerByEmail(email: String): Optional<Customer>
}