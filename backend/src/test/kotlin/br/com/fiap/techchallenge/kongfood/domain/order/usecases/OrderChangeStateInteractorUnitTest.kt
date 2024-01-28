package br.com.fiap.techchallenge.kongfood.domain.order.usecases

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.order.OrderProvider
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderPresenter
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderResponseFormatter
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.repository.OrderRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KongFoodApplicationTests::class])
class OrderChangeStateInteractorUnitTest {

    private final val orderRepository: OrderRepository = mock()
    private final val orderPresenter: OrderPresenter = OrderResponseFormatter()
    private final val orderSearchBoundary: OrderSearchBoundary = mock()
    val orderChangeStateInteractor = OrderChangeStateInteractor(orderRepository, orderPresenter, orderSearchBoundary)

    @Test
    fun `should change order state`() {
        val orderToBePrepared =
            OrderProvider().getCreatedOrderWithSpecificState(UUID.randomUUID(), OrderStatus.IN_PREPARATION)
        Mockito.`when`(orderSearchBoundary.getOrder(orderToBePrepared.id)).thenReturn(orderToBePrepared)

        val result = orderChangeStateInteractor.changeState(orderToBePrepared.id, OrderStatus.READY)

        Mockito.verify(orderSearchBoundary, Mockito.times(1)).getOrder(orderToBePrepared.id)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
        Assertions.assertNotNull(result)
    }

    @Test
    fun `should cancel an order`() {
        val orderToBeCanceled = OrderProvider().getCreatedOrderWithSpecificState(UUID.randomUUID(), OrderStatus.PENDING)
        Mockito.`when`(orderSearchBoundary.getOrder(orderToBeCanceled.id)).thenReturn(orderToBeCanceled)

        val result = orderChangeStateInteractor.cancelOrder(orderToBeCanceled.id)

        Mockito.verify(orderSearchBoundary, Mockito.times(1)).getOrder(orderToBeCanceled.id)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
        Assertions.assertNotNull(result)
    }

    @Test
    fun `should finish an order`() {
        val orderToBeFinished = OrderProvider().getCreatedOrderWithSpecificState(UUID.randomUUID(), OrderStatus.READY)
        Mockito.`when`(orderSearchBoundary.getOrder(orderToBeFinished.id)).thenReturn(orderToBeFinished)

        val result = orderChangeStateInteractor.finishOrder(orderToBeFinished.id)

        Mockito.verify(orderSearchBoundary, Mockito.times(1)).getOrder(orderToBeFinished.id)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
        Assertions.assertNotNull(result)
    }
}