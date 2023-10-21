package br.com.fiap.techchallenge.kongfood.domain.order

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class OrderUnitTest {

    private val clientId: UUID = UUID.randomUUID()

    @Test
    fun `should generate a track order cod`() {
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
    fun `should throw exception when try to finish a order that is not ready`() {
        val order = OrderProvider().getCreatedOrder(clientId)
        assertThrows(DomainException::class.java) {
            order.complete()
        }
    }

    @Test
    fun `should complete a order`() {
        val order = OrderProvider().getCreatedOrderWithSpecificState(clientId, OrderStatus.READY)
        order.complete()
        assertAll(
            { assertEquals(order.status, OrderStatus.FINISHED, "Order status should be FINISHED") },
            { assertNotNull(order.finishedDateTime, "Finished date time should not be null") }
        )
    }

    @Test
    fun `should throw exception when try to cancel a order that is finished`() {
        val order = OrderProvider().getCreatedCompletedOrder(clientId)
        assertThrows(DomainException::class.java) {
            order.cancel()
        }
    }

    @Test
    fun `should cancel a order`() {
        val order = OrderProvider().getCreatedOrderWithSpecificState(clientId, OrderStatus.PENDING)
        order.cancel()
        assertAll(
            { assertEquals(order.status, OrderStatus.CANCELED, "Order status should be CANCELED") },
            { assertNotNull(order.finishedDateTime, "Finished date time should not be null") }
        )
    }

    @Test
    fun `should add a order line`() {
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
            { assertEquals(order.lines[0].product, product, "Order line product should be equals to $product") }
        )
    }

    @Test
    fun `should throw exception when try to add a order line to a finished order`() {
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
}