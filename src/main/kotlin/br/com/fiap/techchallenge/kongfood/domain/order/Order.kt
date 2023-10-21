package br.com.fiap.techchallenge.kongfood.domain.order

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

class Order(
    val id: UUID,
    val lines: MutableList<OrderLine>,
    var status: OrderStatus,
    var total: BigDecimal,
    val clientId: UUID? = null,
    val initialDateTime: LocalDateTime = LocalDateTime.now(),
    var finishedDateTime: LocalDateTime? = null
) {

    fun addOrderLine(product: Product, quantity: Int) {
        validateState()
        val orderLine = OrderLine(product, quantity)
        lines.add(orderLine)
        updateTotal()
    }

    fun removeOrderLine(product: Product) {
        validateState()
        val orderLine = lines.find { it.product == product }
        if (orderLine != null) {
            lines.remove(orderLine)
        }
        updateTotal()
    }

    fun chageState(status: OrderStatus) {
        validateNotFinishedOrCanceled()
        this.status = status
    }

    fun cancel() {
        validateNotFinishedOrReady()
        this.status = OrderStatus.CANCELED
        this.finishedDateTime = LocalDateTime.now()
    }

    fun complete() {
        validateIsReady()
        this.status = OrderStatus.FINISHED
        this.finishedDateTime = LocalDateTime.now()
    }

    private fun validateIsReady() {
        if (status != OrderStatus.READY) {
            throw DomainException("Order must be READY to complete")
        }
    }

    private fun validateNotFinishedOrReady() {
        if (status == OrderStatus.FINISHED || status == OrderStatus.READY) {
            throw DomainException("Order must not be FINISHED or READY to cancel")
        }
    }

    private fun validateNotFinishedOrCanceled() {
        if (status == OrderStatus.FINISHED || status == OrderStatus.CANCELED) {
            throw DomainException("Order must not be FINISHED or CANCELED to change status")
        }
    }

    private fun updateTotal() {
        total = lines.map { it.product.price.multiply(BigDecimal(it.quantity)) }
            .fold(BigDecimal.ZERO) { acc, price -> acc.add(price) }
    }

    private fun validateState() {
        if (status != OrderStatus.FINISHED || status != OrderStatus.CANCELED || status != OrderStatus.READY) {
            throw DomainException("Order must not be FINISHED, CANCELED or READY to add or remove order line")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Order

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}