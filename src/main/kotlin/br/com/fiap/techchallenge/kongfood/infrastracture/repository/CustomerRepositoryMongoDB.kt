package br.com.fiap.techchallenge.kongfood.infrastracture.repository

import br.com.fiap.techchallenge.kongfood.domain.customer.Customer
import br.com.fiap.techchallenge.kongfood.domain.customer.repository.CustomerRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.util.*

@Component
@Primary
class CustomerRepositoryMongoDB(
    val customerRepository: CustomerRepositorySpringDataMongo
) : CustomerRepository {
    override fun save(customer: Customer) {
        customerRepository.save(customer)
    }

    override fun findClientByCpf(cpf: String): Optional<Customer> {
        return customerRepository.findByCpf(cpf)
    }

    override fun findClientById(id: UUID): Optional<Customer> {
        return customerRepository.findById(id)
    }
}