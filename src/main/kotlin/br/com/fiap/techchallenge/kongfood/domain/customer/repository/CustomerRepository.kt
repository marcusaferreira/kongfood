package br.com.fiap.techchallenge.kongfood.domain.customer.repository

import br.com.fiap.techchallenge.kongfood.domain.customer.Customer
import java.util.*

interface CustomerRepository {

    fun save(customer: Customer)

    fun findClientByCpf(cpf: String): Optional<Customer>

    fun findClientById(id: UUID): Optional<Customer>
}