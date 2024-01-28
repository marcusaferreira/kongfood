package br.com.fiap.techchallenge.kongfood.domain.order.usecases

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderFactory
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderFactoryImpl
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
class OrderRegisterInteractorUnitTest {

    private final val orderRepository: OrderRepository = mock()
    private final val orderPresenter: OrderPresenter = OrderResponseFormatter()
    private final val orderFactory: OrderFactory = OrderFactoryImpl()
    val orderRegisterInteractor = OrderRegisterInteractor(orderRepository, orderPresenter, orderFactory)

    @Test
    fun `should create an order`() {
        val orderId = orderRegisterInteractor.create(UUID.randomUUID())

        Assertions.assertNotNull(orderId)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should create an order without client identification`() {
        val orderId = orderRegisterInteractor.create(null)

        Assertions.assertNotNull(orderId)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
    }

}