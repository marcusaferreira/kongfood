package br.com.fiap.techchallenge.kongfood.frameworks.drivers.db.repository

import br.com.fiap.techchallenge.kongfood.domain.order.entities.Order
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface OrderRepositorySpringDataMongo : MongoRepository<Order, UUID> {
    fun findAllByInitialDateTimeBetween(todayMidnight: LocalDateTime?, tomorrowMidnight: LocalDateTime?): List<Order>
}