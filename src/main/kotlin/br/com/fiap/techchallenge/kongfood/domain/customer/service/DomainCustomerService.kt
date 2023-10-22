package br.com.fiap.techchallenge.kongfood.domain.customer.service

import br.com.fiap.techchallenge.kongfood.domain.customer.repository.CustomerRepository
import java.util.*

class DomainCustomerService(
    val customerRepository: CustomerRepository
) : CustomerService{
    override fun createClient(name: String, email: String, phone: String?, cpf: String?): UUID {
        TODO("Not yet implemented")
    }

    override fun updateClient(id: UUID, name: String, email: String, phone: String?, cpf: String?) {
        TODO("Not yet implemented")
    }

    override fun deActivateClient(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun findClientByCpf(cpf: String): UUID? {
        TODO("Not yet implemented")
    }
}