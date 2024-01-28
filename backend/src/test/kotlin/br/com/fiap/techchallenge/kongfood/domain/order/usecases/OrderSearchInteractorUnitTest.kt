package br.com.fiap.techchallenge.kongfood.domain.order.usecases

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.order.OrderProvider
import br.com.fiap.techchallenge.kongfood.domain.order.entities.Order
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderPresenter
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderResponseFormatter
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.repository.OrderRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KongFoodApplicationTests::class])
class OrderSearchInteractorUnitTest {

    private val orderRepository: OrderRepository = mock()
    private val orderPresenter: OrderPresenter = OrderResponseFormatter()
    val orderSearchInteractor = OrderSearchInteractor(orderRepository, orderPresenter)
    @Test
    fun `should get order data`(){
        Mockito.`when`(orderRepository.findById(orderMock!!.id)).thenReturn(
            Optional.of(
                orderMock!!))

        val result = orderSearchInteractor.getOrderData(orderMock!!.id)

        Mockito.verify(orderRepository, Mockito.times(1)).findById(orderMock!!.id)
        Assertions.assertNotNull(result)
    }

    @Test
    fun `should list orders of the day by state`(){
        Mockito.`when`(orderRepository.findOrdersOfTheDayByStatus(OrderStatus.CREATED)).thenReturn(listOf(
            orderMock!!))

        val results = orderSearchInteractor.listOrdersOfTheDayByState(OrderStatus.CREATED)

        Mockito.verify(orderRepository, Mockito.times(1)).findOrdersOfTheDayByStatus(OrderStatus.CREATED)
        Assertions.assertNotNull(results)
        Assertions.assertEquals(1, results.size)

    }

    @Test
    fun `should list all orders of the day ordered by date, from the most recent to the oldest and grouped by status, READY first, then IN_PREPARATION, then ACCEPTED`() {
        val orderReady = OrderProvider().getCreatedOrderWithSpecificState(UUID.randomUUID(), OrderStatus.READY)
        val orderInPreparation =
            OrderProvider().getCreatedOrderWithSpecificState(UUID.randomUUID(), OrderStatus.IN_PREPARATION)
        val orderAccepted = OrderProvider().getCreatedOrderWithSpecificState(UUID.randomUUID(), OrderStatus.ACCEPTED)

        Mockito.`when`(orderRepository.findOrdersOfTheDayByStatus(OrderStatus.READY)).thenReturn(listOf(orderReady))
        Mockito.`when`(orderRepository.findOrdersOfTheDayByStatus(OrderStatus.IN_PREPARATION))
            .thenReturn(listOf(orderInPreparation))
        Mockito.`when`(orderRepository.findOrdersOfTheDayByStatus(OrderStatus.ACCEPTED))
            .thenReturn(listOf(orderAccepted))

        val listedORders = orderSearchInteractor.listPriorityOrdersOfTheDay()

        Mockito.verify(orderRepository, Mockito.times(1)).findOrdersOfTheDayByStatus(OrderStatus.READY)
        Mockito.verify(orderRepository, Mockito.times(1)).findOrdersOfTheDayByStatus(OrderStatus.IN_PREPARATION)
        Mockito.verify(orderRepository, Mockito.times(1)).findOrdersOfTheDayByStatus(OrderStatus.ACCEPTED)

        Assertions.assertEquals(3, listedORders.size)
        Assertions.assertEquals(orderReady.id, listedORders[0].id)
        Assertions.assertEquals(orderInPreparation.id, listedORders[1].id)
        Assertions.assertEquals(orderAccepted.id, listedORders[2].id)
    }

    @Test
    fun `should throw exception when try to get order data with invalid order id`(){
        Mockito.`when`(orderRepository.findById(orderMock!!.id)).thenReturn(Optional.empty())

        Assertions.assertThrows(DomainException::class.java) {
            orderSearchInteractor.getOrderData(orderMock!!.id)
        }
        Mockito.verify(orderRepository, Mockito.times(1)).findById(orderMock!!.id)
    }

    @Test
    fun `should throw exception when try to get priority orders of the day and there is no orders`(){
        Mockito.`when`(orderRepository.findOrdersOfTheDayByStatus(OrderStatus.READY)).thenReturn(listOf())
        Mockito.`when`(orderRepository.findOrdersOfTheDayByStatus(OrderStatus.IN_PREPARATION)).thenReturn(listOf())
        Mockito.`when`(orderRepository.findOrdersOfTheDayByStatus(OrderStatus.ACCEPTED)).thenReturn(listOf())

        Assertions.assertThrows(DomainException::class.java) {
            orderSearchInteractor.listPriorityOrdersOfTheDay()
        }
        Mockito.verify(orderRepository, Mockito.times(1)).findOrdersOfTheDayByStatus(OrderStatus.READY)
        Mockito.verify(orderRepository, Mockito.times(1)).findOrdersOfTheDayByStatus(OrderStatus.IN_PREPARATION)
        Mockito.verify(orderRepository, Mockito.times(1)).findOrdersOfTheDayByStatus(OrderStatus.ACCEPTED)
    }

    @Test
    fun `should get an order`(){
        Mockito.`when`(orderRepository.findById(orderMock!!.id)).thenReturn(Optional.of(orderMock!!))

        val result = orderSearchInteractor.getOrder(orderMock!!.id)

        Mockito.verify(orderRepository, Mockito.times(1)).findById(orderMock!!.id)
        Assertions.assertNotNull(result)
    }

    @Test
    fun `should throw exception when try to get an order with invalid order id`(){
        Mockito.`when`(orderRepository.findById(orderMock!!.id)).thenReturn(Optional.empty())

        Assertions.assertThrows(DomainException::class.java) {
            orderSearchInteractor.getOrder(orderMock!!.id)
        }
        Mockito.verify(orderRepository, Mockito.times(1)).findById(orderMock!!.id)
    }

    companion object{
        var orderMock: Order? = null
        @BeforeAll
        @JvmStatic
        internal fun generateTestData(){
            orderMock = OrderProvider().getCreatedOrder(orderId = UUID.randomUUID(), clientId = UUID.randomUUID())
        }
    }
}