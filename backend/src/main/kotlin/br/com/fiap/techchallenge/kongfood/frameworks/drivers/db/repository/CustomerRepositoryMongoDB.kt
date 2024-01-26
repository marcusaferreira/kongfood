package br.com.fiap.techchallenge.kongfood.frameworks.drivers.db.repository

import br.com.fiap.techchallenge.kongfood.domain.customer.entities.CPF
import br.com.fiap.techchallenge.kongfood.domain.customer.entities.Customer
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerRequestModel
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.repository.CustomerRepository
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

    override fun findCustomerByCpf(cpf: String): Optional<Customer> {
        return customerRepository.findByCpf(CPF(cpf))
    }

    override fun findCustomerById(id: UUID): Optional<Customer> {
        return customerRepository.findById(id)
    }

    override fun findCustomerByEmail(email: String): Optional<Customer> {
        return customerRepository.findByEmail(email)
    }

    override fun verifyIfCustomerByEmailAlreadyExists(customerRequestModel: CustomerRequestModel): Boolean {
        val customer = findCustomerByEmail(customerRequestModel.email)
        return customer.isPresent
    }

}