package br.com.fiap.techchallenge.kongfood.domain.customer.service

import br.com.fiap.techchallenge.kongfood.domain.customer.service.dto.CustomerDTO
import java.util.UUID

interface CustomerService {

    fun createCustomer(customerDTO: CustomerDTO): UUID

    fun updateCustomer(customerDTO: CustomerDTO, id: String): CustomerDTO

    fun deActivateCustomer(id: String)

    fun findCustomerByCpf(cpf: String): CustomerDTO

    fun findCustomerById(id: String): CustomerDTO

    fun findCustomerByEmail(email: String): CustomerDTO

    fun activateCustomer(id: String)
}