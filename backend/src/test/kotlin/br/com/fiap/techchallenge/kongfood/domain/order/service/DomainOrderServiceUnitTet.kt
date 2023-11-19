package br.com.fiap.techchallenge.kongfood.domain.order.service

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.order.Order
import br.com.fiap.techchallenge.kongfood.domain.order.OrderProvider
import br.com.fiap.techchallenge.kongfood.domain.order.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.Product
import br.com.fiap.techchallenge.kongfood.domain.order.repository.OrderRepository
import br.com.fiap.techchallenge.kongfood.domain.order.service.dto.OrderLineDTO
import br.com.fiap.techchallenge.kongfood.domain.product.ProductCategory
import br.com.fiap.techchallenge.kongfood.domain.product.service.ProductService
import br.com.fiap.techchallenge.kongfood.domain.product.service.dto.ProductDTO
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

    private final val orderRepository: OrderRepository = mock()
    private final val productService: ProductService = mock()
    val orderService = DomainOrderService(orderRepository, productService)

    @Test
    fun `should create an order`() {
        val orderId = orderService.createOrder(UUID.randomUUID())

        Assertions.assertNotNull(orderId)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should create an order without client identification`() {
        val orderId = orderService.createOrder(null)

        Assertions.assertNotNull(orderId)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should add an order line`() {
        Mockito.`when`(productService.findProductById(orderLineMockDTO!!.productId)).thenReturn(
            ProductDTO(
                orderLineMockDTO!!.productId.toString(), orderLineMockDTO!!.price.toString(),
                orderLineMockDTO!!.name,
                orderLineMockDTO!!.description,
                orderLineMockDTO!!.category,
                true
            )
        )
        Mockito.`when`(orderRepository.findById(orderMock!!.id)).thenReturn(Optional.of(orderMock!!))

        orderService.addOrderLine(orderMock!!.id, orderLineMockDTO!!)

        Mockito.verify(productService, Mockito.times(1)).findProductById(orderLineMockDTO!!.productId)
        Mockito.verify(orderRepository, Mockito.times(1)).findById(orderMock!!.id)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should remove an order line`(){
        val product = Product( orderLineMockDTO!!.productId,
            orderLineMockDTO!!.price,
            orderLineMockDTO!!.name,
            orderLineMockDTO!!.description,
            ProductCategory.MAIN_COURSES)
        orderMock!!.addOrderLine(product, orderLineMockDTO!!.quantity,
            orderLineMockDTO!!.note)

        Mockito.`when`(productService.findProductById(orderLineMockDTO!!.productId)).thenReturn(
            ProductDTO(
                orderLineMockDTO!!.productId.toString(), orderLineMockDTO!!.price.toString(),
                orderLineMockDTO!!.name,
                orderLineMockDTO!!.description,
                orderLineMockDTO!!.category,
                true
            )
        )
        Mockito.`when`(orderRepository.findById(orderMock!!.id)).thenReturn(Optional.of(orderMock!!))

        orderService.removeOrderLine(orderMock!!.id, orderLineMockDTO!!)

        Mockito.verify(productService, Mockito.times(1)).findProductById(orderLineMockDTO!!.productId)
        Mockito.verify(orderRepository, Mockito.times(1)).findById(orderMock!!.id)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should confirm an order`(){
        val orderToBeConfirmed = OrderProvider().getCreatedOrderWithSpecificState(UUID.randomUUID(), OrderStatus.PENDING)
        Mockito.`when`(orderRepository.findById(orderToBeConfirmed.id)).thenReturn(Optional.of(orderToBeConfirmed))

        orderService.confirmOrder(orderToBeConfirmed.id)

        Mockito.verify(orderRepository, Mockito.times(1)).findById(orderToBeConfirmed.id)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should prepare an order`(){
        val orderToBePrepared = OrderProvider().getCreatedOrderWithSpecificState(UUID.randomUUID(), OrderStatus.ACCEPTED)
        Mockito.`when`(orderRepository.findById(orderToBePrepared.id)).thenReturn(Optional.of(orderToBePrepared))

        orderService.prepareOrder(orderToBePrepared.id)

        Mockito.verify(orderRepository, Mockito.times(1)).findById(orderToBePrepared.id)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should notify an order prepared`(){
        val orderToBePrepared = OrderProvider().getCreatedOrderWithSpecificState(UUID.randomUUID(), OrderStatus.IN_PREPARATION)
        Mockito.`when`(orderRepository.findById(orderToBePrepared.id)).thenReturn(Optional.of(orderToBePrepared))

        orderService.notifyPreparedOrder(orderToBePrepared.id)

        Mockito.verify(orderRepository, Mockito.times(1)).findById(orderToBePrepared.id)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should cancel an order`(){
        val orderToBeCanceled = OrderProvider().getCreatedOrderWithSpecificState(UUID.randomUUID(), OrderStatus.PENDING)
        Mockito.`when`(orderRepository.findById(orderToBeCanceled.id)).thenReturn(Optional.of(orderToBeCanceled))

        orderService.cancelOrder(orderToBeCanceled.id)

        Mockito.verify(orderRepository, Mockito.times(1)).findById(orderToBeCanceled.id)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should finish an order`(){
        val orderToBeFinished = OrderProvider().getCreatedOrderWithSpecificState(UUID.randomUUID(), OrderStatus.READY)
        Mockito.`when`(orderRepository.findById(orderToBeFinished.id)).thenReturn(Optional.of(orderToBeFinished))

        orderService.confirmOrder(orderToBeFinished.id)

        Mockito.verify(orderRepository, Mockito.times(1)).findById(orderToBeFinished.id)
        Mockito.verify(orderRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should get order data`(){
        Mockito.`when`(orderRepository.findById(orderMock!!.id)).thenReturn(Optional.of(orderMock!!))

        orderService.getOrderData(orderMock!!.id)

        Mockito.verify(orderRepository, Mockito.times(1)).findById(orderMock!!.id)
    }

    @Test
    fun `should list orders of the day by state`(){
        Mockito.`when`(orderRepository.findOrderOfTheDayByStatus(OrderStatus.CREATED)).thenReturn(listOf(orderMock!!))

        orderService.listOrdersOfTheDayByState(OrderStatus.CREATED)

        Mockito.verify(orderRepository, Mockito.times(1)).findOrderOfTheDayByStatus(OrderStatus.CREATED)
    }

    companion object{
        var orderMock: Order? = null
        var orderLineMockDTO: OrderLineDTO? = null

        @BeforeAll
        @JvmStatic
        internal fun generateTestData(){
            orderMock = OrderProvider().getCreatedOrder(orderId = UUID.randomUUID(), clientId = UUID.randomUUID())
            orderLineMockDTO = OrderLineDTO(
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