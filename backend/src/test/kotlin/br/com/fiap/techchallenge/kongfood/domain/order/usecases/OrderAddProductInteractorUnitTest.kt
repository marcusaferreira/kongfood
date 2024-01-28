package br.com.fiap.techchallenge.kongfood.domain.order.usecases

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.order.OrderProvider
import br.com.fiap.techchallenge.kongfood.domain.order.entities.Order
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderProductFactory
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderProductFactoryImpl
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderLineRequestModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderPresenter
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderResponseFormatter
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.repository.OrderRepository
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductCategory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.math.BigDecimal
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KongFoodApplicationTests::class])
class OrderAddProductInteractorUnitTest {
    private final val orderRepository: OrderRepository = mock()
    private final val orderPresenter: OrderPresenter = OrderResponseFormatter()
    private final val orderSearchBoundary: OrderSearchBoundary = mock()
    private final val orderProductFactory: OrderProductFactory = OrderProductFactoryImpl()
    val orderAddProductInteractor = OrderAddProductInteractor(
        orderRepository,
        orderPresenter,
        orderSearchBoundary,
        orderProductFactory
    )

    @Test
    fun `should add an order line`() {
        Mockito.`when`(orderSearchBoundary.getOrder(orderMock!!.id)).thenReturn(
                orderMock!!)

        orderAddProductInteractor.addOrderLine(orderMock!!.id, orderLineMockDTO!!)

        Mockito.verify(orderSearchBoundary, Mockito.times(1)).getOrder(orderMock!!.id)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
    }

    companion object{
        var orderMock: Order? = null
        var orderLineMockDTO: OrderLineRequestModel? = null

        @BeforeAll
        @JvmStatic
        internal fun generateTestData(){
            orderMock = OrderProvider().getCreatedOrder(orderId = UUID.randomUUID(), clientId = UUID.randomUUID())
            orderLineMockDTO = OrderLineRequestModel(
                UUID.randomUUID(),
                "Hamburguer",
                "a delicious hamburguer",
                BigDecimal.valueOf(10),
                ProductCategory.MAIN_COURSES.type,
                1,
                "no onions"
            )
        }
    }
}