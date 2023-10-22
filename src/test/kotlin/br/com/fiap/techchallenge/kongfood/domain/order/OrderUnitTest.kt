package br.com.fiap.techchallenge.kongfood.domain.order

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.DomainException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KongFoodApplicationTests::class])
class OrderUnitTest {

    private val clientId: UUID = UUID.randomUUID()

    @Test
    fun `should generate an track order cod`() {
        val order = OrderProvider().getCreatedOrder(clientId)
        val lastOrderNumber = 0
        order.generateTrackOrderCode(lastOrderNumber)
        val trackCode = "${LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMYYYY"))} - 00001"

        assertAll(
            { assertNotNull(order.trackOrderCode != null, "Track order code should not be null") },
            { assertEquals(order.trackOrderCode, trackCode, "Track order code should be equals to $trackCode") }
        )
    }

    @Test
    fun `should throw exception when try to finish an order that is not ready`() {
        val order = OrderProvider().getCreatedOrder(clientId)
        assertThrows(DomainException::class.java) {
            order.complete()
        }
    }

    @Test
    fun `should complete an order`() {
        val order = OrderProvider().getCreatedOrderWithSpecificState(clientId, OrderStatus.READY)
        order.complete()
        assertAll(
            { assertEquals(order.status, OrderStatus.FINISHED, "Order status should be FINISHED") },
            { assertNotNull(order.finishedDateTime, "Finished date time should not be null") }
        )
    }

    @Test
    fun `should throw exception when try to cancel an order that is finished`() {
        val order = OrderProvider().getCreatedCompletedOrder(clientId)
        assertThrows(DomainException::class.java) {
            order.cancel()
        }
    }

    @Test
    fun `should cancel an order`() {
        val order = OrderProvider().getCreatedOrderWithSpecificState(clientId, OrderStatus.PENDING)
        order.cancel()
        assertAll(
            { assertEquals(order.status, OrderStatus.CANCELED, "Order status should be CANCELED") },
            { assertNotNull(order.finishedDateTime, "Finished date time should not be null") }
        )
    }

    @Test
    fun `should add an order line`() {
        val order = OrderProvider().getCreatedOrder(clientId)
        val product = Product(
            UUID.randomUUID(),
            BigDecimal.valueOf(100.00),
            "Product 1",
            "Product 1 description",
            ProductCategory.SIDE_DISHES,
            true
        )
        order.addOrderLine(product, 1)
        assertAll(
            { assertEquals(order.lines.size, 1, "Order lines size should be 1") },
            { assertEquals(order.lines[0].product, product, "Order line product should be equals to $product") },
            { assertEquals(order.total, BigDecimal.valueOf(100.00), "Order total is not correct") }
        )
    }

    @Test
    fun `should throw exception when try to add an order line to a finished order`() {
        val order = OrderProvider().getCreatedCompletedOrder(clientId)
        val product = Product(
            UUID.randomUUID(),
            BigDecimal.valueOf(100.00),
            "Product 1",
            "Product 1 description",
            ProductCategory.SIDE_DISHES,
            true
        )
        assertThrows(DomainException::class.java) {
            order.addOrderLine(product, 1)
        }
    }

    @Test
    fun `should remove an order line`() {
        val order = OrderProvider().getCreatedOrder(clientId)
        val product = Product(
            UUID.randomUUID(),
            BigDecimal.valueOf(100.00),
            "Product 1",
            "Product 1 description",
            ProductCategory.SIDE_DISHES,
            true
        )
        order.addOrderLine(product, 1)
        order.removeOrderLine(product)
        assertAll(
            { assertEquals(order.lines.size, 0, "Order lines size should be 0") },
            { assertEquals(order.total.setScale(1), BigDecimal.valueOf(0.00), "Order total is not correct") }
        )
    }

    @Test
    fun `should throw exception when try to remove an order line to a finished order`() {
        val order = OrderProvider().getCreatedCompletedOrder(clientId)
        val product = Product(
            UUID.randomUUID(),
            BigDecimal.valueOf(100.00),
            "Product 1",
            "Product 1 description",
            ProductCategory.SIDE_DISHES,
            true
        )
        assertThrows(DomainException::class.java) {
            order.removeOrderLine(product)
        }
    }

    @Test
    fun `should change order state`() {
        val order = OrderProvider().getCreatedOrder(clientId)
        order.chageState(OrderStatus.PENDING)
        assertAll(
            { assertEquals(order.status, OrderStatus.PENDING, "Order status should be PENDING") }
        )
    }
    @Test
    fun `should throw exception when try to change order state to finished`() {
        val order = OrderProvider().getCreatedCompletedOrder(clientId)
        assertThrows(DomainException::class.java) {
            order.chageState(OrderStatus.FINISHED)
        }
    }
}