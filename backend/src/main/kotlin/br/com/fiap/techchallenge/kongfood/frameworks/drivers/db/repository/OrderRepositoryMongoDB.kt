package br.com.fiap.techchallenge.kongfood.frameworks.drivers.db.repository

import br.com.fiap.techchallenge.kongfood.domain.order.entities.Order
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.repository.OrderRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

@Component
@Primary
class OrderRepositoryMongoDB(
    val orderRepository: OrderRepositorySpringDataMongo
): OrderRepository {
    override fun findById(id: UUID): Optional<Order> {
        return orderRepository.findById(id)
    }

    override fun findAll(): List<Order> {
        return orderRepository.findAll()
    }

    override fun save(order: Order) {
        orderRepository.save(order)
    }

    override fun countOrdersOfTheDay(): Int {
        val midnight = LocalTime.MIDNIGHT
        val today = LocalDate.now(ZoneId.of("Europe/Berlin"))
        val todayMidnight = LocalDateTime.of(today, midnight)
        val tomorrowMidnight = todayMidnight.plusDays(1)
        return orderRepository.findAllByInitialDateTimeBetween(todayMidnight, tomorrowMidnight).size
    }

    override fun findOrdersOfTheDayByStatus(status: OrderStatus): List<Order> {
        val midnight = LocalTime.MIDNIGHT
        val today = LocalDate.now(ZoneId.of("Europe/Berlin"))
        val todayMidnight = LocalDateTime.of(today, midnight)
        val tomorrowMidnight = todayMidnight.plusDays(1)
        return orderRepository.findAllByInitialDateTimeBetween(todayMidnight, tomorrowMidnight)
            .filter { it.status == status }
    }
}