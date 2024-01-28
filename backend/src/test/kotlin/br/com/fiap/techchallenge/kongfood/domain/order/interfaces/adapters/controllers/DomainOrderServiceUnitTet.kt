package br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.controllers

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.order.OrderProvider
import br.com.fiap.techchallenge.kongfood.domain.order.entities.Order
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderLineRequestModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderPresenter
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderResponseFormatter
import br.com.fiap.techchallenge.kongfood.domain.order.usecases.*
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductCategory
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.controllers.ProductService
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductResponseModel
import org.junit.jupiter.api.Assertions
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
class DomainOrderServiceUnitTet {

    private final val productService: ProductService = mock()
    private final val orderSearchBoundary: OrderSearchBoundary = mock()
    private final val orderRegisterBoundary: OrderRegisterBoundary = mock()
    private final val orderAddProductBoundary: OrderAddProductBoundary = mock()
    private final val orderPresenter: OrderPresenter = OrderResponseFormatter()
    private final val orderRemoveProductBoundary: OrderRemoveProductBoundary = mock()
    private final val orderChangeStateBoundary: OrderChangeStateBoundary = mock()
    val orderService = DomainOrderService(
        productService,
        orderSearchBoundary,
        orderRegisterBoundary,
        orderAddProductBoundary,
        orderPresenter,
        orderRemoveProductBoundary,
        orderChangeStateBoundary
    )

    @Test
    fun `should create an order`() {
        Mockito.`when`(orderRegisterBoundary.create(any())).thenReturn(
            OrderProvider().getCreatedOrderResponse(UUID.randomUUID())
        )

        val orderId = orderService.createOrder(UUID.randomUUID())

        Assertions.assertNotNull(orderId)
        Mockito.verify(orderRegisterBoundary, Mockito.times(1)).create(any())
    }

    @Test
    fun `should throw exception when try to add an order line with invalid product`() {
        Mockito.`when`(productService.findProductById(orderLineMockDTO!!.productId))
            .thenReturn(
                ProductResponseModel(
                    id = UUID.randomUUID().toString(),
                    name = "Hamburguer",
                    description = "A delicious hamburguer",
                    price = "10.0",
                    category = ProductCategory.MAIN_COURSES.type,
                    status = false
                )
            )

        Assertions.assertThrows(DomainException::class.java) {
            orderService.addOrderLine(orderMock!!.id, orderLineMockDTO!!)
        }
        Mockito.verify(productService, Mockito.times(1)).findProductById(orderLineMockDTO!!.productId)
    }

    @Test
    fun `should throw exception when try to add an order line with inactive product`() {
        Mockito.`when`(productService.findProductById(orderLineMockDTO!!.productId)).thenReturn(
            ProductResponseModel(
                orderLineMockDTO!!.productId.toString(), orderLineMockDTO!!.price.toString(),
                orderLineMockDTO!!.name,
                orderLineMockDTO!!.description,
                orderLineMockDTO!!.category,
                false
            )
        )

        Assertions.assertThrows(DomainException::class.java) {
            orderService.addOrderLine(orderMock!!.id, orderLineMockDTO!!)
        }
        Mockito.verify(productService, Mockito.times(1)).findProductById(orderLineMockDTO!!.productId)
    }

    @Test
    fun `should add an order line`() {
        Mockito.`when`(productService.findProductById(orderLineMockDTO!!.productId)).thenReturn(
            ProductResponseModel(
                orderLineMockDTO!!.productId.toString(), orderLineMockDTO!!.price.toString(),
                orderLineMockDTO!!.name,
                orderLineMockDTO!!.description,
                orderLineMockDTO!!.category,
                true
            )
        )
        Mockito.`when`(orderAddProductBoundary.addOrderLine(any(), any())).thenReturn(
            OrderProvider().getCreatedOrderResponse(UUID.randomUUID())
        )

        orderService.addOrderLine(orderMock!!.id, orderLineMockDTO!!)

        Mockito.verify(productService, Mockito.times(1)).findProductById(orderLineMockDTO!!.productId)
        Mockito.verify(orderAddProductBoundary, Mockito.times(1)).addOrderLine(any(), any())
    }

    @Test
    fun `should throw exception when try to remove an order line with invalid product`() {
        Mockito.`when`(productService.findProductById(orderLineMockDTO!!.productId)).thenReturn(
            ProductResponseModel(
                orderLineMockDTO!!.productId.toString(), orderLineMockDTO!!.price.toString(),
                orderLineMockDTO!!.name,
                orderLineMockDTO!!.description,
                orderLineMockDTO!!.category,
                false
            )
        )

        Assertions.assertThrows(DomainException::class.java) {
            orderService.removeOrderLine(orderMock!!.id, orderLineMockDTO!!)
        }
        Mockito.verify(productService, Mockito.times(1)).findProductById(orderLineMockDTO!!.productId)
    }

    //should remove an order line
    @Test
    fun `should remove an order line`() {
        Mockito.`when`(productService.findProductById(orderLineMockDTO!!.productId)).thenReturn(
            ProductResponseModel(
                orderLineMockDTO!!.productId.toString(), orderLineMockDTO!!.price.toString(),
                orderLineMockDTO!!.name,
                orderLineMockDTO!!.description,
                orderLineMockDTO!!.category,
                true
            )
        )
        Mockito.`when`(orderRemoveProductBoundary.removeOrderLine(any(), any())).thenReturn(
            OrderProvider().getCreatedOrderResponse(UUID.randomUUID())
        )

        orderService.removeOrderLine(orderMock!!.id, orderLineMockDTO!!)

        Mockito.verify(productService, Mockito.times(1)).findProductById(orderLineMockDTO!!.productId)
        Mockito.verify(orderRemoveProductBoundary, Mockito.times(1)).removeOrderLine(any(), any())
    }

    @Test
    fun `should confirm an order`() {
        Mockito.`when`(orderChangeStateBoundary.changeState(orderMock!!.id, OrderStatus.ACCEPTED)).thenReturn(
            orderPresenter.prepareSuccessResponse(
                OrderProvider().getCreatedOrderWithSpecificState(
                    UUID.randomUUID(),
                    OrderStatus.ACCEPTED
                )
            )
        )

        orderService.confirmOrder(orderMock!!.id)

        Mockito.verify(orderChangeStateBoundary, Mockito.times(1)).changeState(orderMock!!.id, OrderStatus.ACCEPTED)
    }

    @Test
    fun `should prepare an order`() {
        Mockito.`when`(orderChangeStateBoundary.changeState(orderMock!!.id, OrderStatus.IN_PREPARATION)).thenReturn(
            orderPresenter.prepareSuccessResponse(
                OrderProvider().getCreatedOrderWithSpecificState(
                    UUID.randomUUID(),
                    OrderStatus.IN_PREPARATION
                )
            )
        )

        orderService.prepareOrder(orderMock!!.id)

        Mockito.verify(orderChangeStateBoundary, Mockito.times(1))
            .changeState(orderMock!!.id, OrderStatus.IN_PREPARATION)
    }

    @Test
    fun `should notify prepared order`() {
        Mockito.`when`(orderChangeStateBoundary.changeState(orderMock!!.id, OrderStatus.READY)).thenReturn(
            orderPresenter.prepareSuccessResponse(
                OrderProvider().getCreatedOrderWithSpecificState(
                    UUID.randomUUID(),
                    OrderStatus.READY
                )
            )
        )

        orderService.notifyPreparedOrder(orderMock!!.id)

        Mockito.verify(orderChangeStateBoundary, Mockito.times(1)).changeState(orderMock!!.id, OrderStatus.READY)
    }

    @Test
    fun `should cancel an order`() {
        Mockito.`when`(orderChangeStateBoundary.cancelOrder(any())).thenReturn(
            orderPresenter.prepareSuccessResponse(
                OrderProvider().getCreatedOrderWithSpecificState(
                    UUID.randomUUID(),
                    OrderStatus.CANCELED
                )
            )
        )

        orderService.cancelOrder(orderMock!!.id)

        Mockito.verify(orderChangeStateBoundary, Mockito.times(1)).cancelOrder(any())
    }

    @Test
    fun `should finish an order`() {
        Mockito.`when`(orderChangeStateBoundary.finishOrder(any())).thenReturn(
            orderPresenter.prepareSuccessResponse(
                OrderProvider().getCreatedOrderWithSpecificState(
                    UUID.randomUUID(),
                    OrderStatus.FINISHED
                )
            )
        )

        orderService.finishOrder(orderMock!!.id)

        Mockito.verify(orderChangeStateBoundary, Mockito.times(1)).finishOrder(any())
    }

    @Test
    fun `should get order data`() {
        Mockito.`when`(orderSearchBoundary.getOrderData(any())).thenReturn(
            orderPresenter.prepareSuccessResponse(
                OrderProvider().getCreatedOrderWithSpecificState(
                    UUID.randomUUID(),
                    OrderStatus.FINISHED
                )
            )
        )

        orderService.getOrderData(orderMock!!.id)

        Mockito.verify(orderSearchBoundary, Mockito.times(1)).getOrderData(any())
    }

    @Test
    fun `should list orders of the day by state`() {
        Mockito.`when`(orderSearchBoundary.listOrdersOfTheDayByState(any())).thenReturn(
            listOf(
                orderPresenter.prepareSuccessResponse(
                    OrderProvider().getCreatedOrderWithSpecificState(
                        UUID.randomUUID(),
                        OrderStatus.FINISHED
                    )
                )
            )
        )

        orderService.listOrdersOfTheDayByState(OrderStatus.FINISHED)

        Mockito.verify(orderSearchBoundary, Mockito.times(1)).listOrdersOfTheDayByState(any())
    }

    @Test
    fun `should list priority orders of the day`() {
        Mockito.`when`(orderSearchBoundary.listPriorityOrdersOfTheDay()).thenReturn(
            listOf(
                orderPresenter.prepareSuccessResponse(
                    OrderProvider().getCreatedOrderWithSpecificState(
                        UUID.randomUUID(),
                        OrderStatus.FINISHED
                    )
                )
            )
        )

        orderService.listPriorityOrdersOfTheDay()

        Mockito.verify(orderSearchBoundary, Mockito.times(1)).listPriorityOrdersOfTheDay()
    }

    companion object {
        var orderMock: Order? = null
        var orderLineMockDTO: OrderLineRequestModel? = null

        @BeforeAll
        @JvmStatic
        internal fun generateTestData() {
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