package br.com.fiap.techchallenge.kongfood.infrastructure.repository

import br.com.fiap.techchallenge.kongfood.domain.customer.Customer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepositorySpringDataMongo: MongoRepository<Customer, UUID> {
    fun findByCpf(cpf: String): Optional<Customer>

}
