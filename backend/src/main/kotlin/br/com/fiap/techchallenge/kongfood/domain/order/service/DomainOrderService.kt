package br.com.fiap.techchallenge.kongfood.domain.order.service

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.order.Order
import br.com.fiap.techchallenge.kongfood.domain.order.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.Product
import br.com.fiap.techchallenge.kongfood.domain.order.repository.OrderRepository
import br.com.fiap.techchallenge.kongfood.domain.order.service.dto.OrderDTO
import br.com.fiap.techchallenge.kongfood.domain.order.service.dto.OrderLineDTO
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductCategory
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.controller.ProductService
import java.math.BigDecimal
import java.util.*

class DomainOrderService(
    val orderRepository: OrderRepository,
    private val productService: ProductService
) : OrderService {

    override fun createOrder(customerId: UUID?): UUID {
        val order = Order(UUID.randomUUID(), mutableListOf(), OrderStatus.CREATED, BigDecimal.ZERO, customerId)
        val lastOrderNumber = orderRepository.countOrdersOfTheDay()
        order.generateTrackOrderCode(lastOrderNumber)
        orderRepository.save(order)

        return order.id
    }

    override fun addOrderLine(orderId: UUID, orderLine: OrderLineDTO) {
        val product = Product(orderLine.productId, orderLine.price, orderLine.name, orderLine.description,
            ProductCategory.getEnumByString(orderLine.category)!!)
        val order = getOrder(orderId)
        verifyProduct(product)
        order.addOrderLine(product, orderLine.quantity, orderLine.note)
        order.chageState(OrderStatus.PENDING)

        orderRepository.save(order)
    }

    override fun removeOrderLine(orderId: UUID, orderLine: OrderLineDTO) {
        val product = Product(orderLine.productId, orderLine.price, orderLine.name, orderLine.description,
            ProductCategory.getEnumByString(orderLine.category)!!)
        val order = getOrder(orderId)
        verifyProduct(product)
        order.removeOrderLine(product)
        if (order.lines.isEmpty()) {
            order.chageState(OrderStatus.CREATED)
        } else {
            order.chageState(OrderStatus.PENDING)
        }

        orderRepository.save(order)
    }

    override fun confirmOrder(orderId: UUID) {
        val order = getOrder(orderId)
        order.chageState(OrderStatus.ACCEPTED)

        orderRepository.save(order)
    }

    override fun prepareOrder(orderId: UUID) {
        val order = getOrder(orderId)
        order.chageState(OrderStatus.IN_PREPARATION)

        orderRepository.save(order)
    }

    override fun notifyPreparedOrder(orderId: UUID) {
        val order = getOrder(orderId)
        order.chageState(OrderStatus.READY)

        orderRepository.save(order)
    }

    override fun cancelOrder(orderId: UUID) {
        val order = getOrder(orderId)
        order.cancel()

        orderRepository.save(order)
    }

    override fun finishOrder(orderId: UUID) {
        val order = getOrder(orderId)
        order.complete()

        orderRepository.save(order)
    }

    override fun getOrderData(orderId: UUID): OrderDTO {
        val order = getOrder(orderId)
        return OrderDTO.from(order)
    }

    override fun listOrdersOfTheDayByState(status: OrderStatus): List<OrderDTO> {
        val orders = orderRepository.findOrdersOfTheDayByStatus(status)
        return orders.map { OrderDTO.from(it)}
    }

    override fun listPriorityOrdersOfTheDay(): List<OrderDTO> {
        val orders = mutableListOf<OrderDTO>()
        orders.addAll(listOrdersOfTheDayByState(OrderStatus.READY))
        orders.addAll(listOrdersOfTheDayByState(OrderStatus.IN_PREPARATION))
        orders.addAll(listOrdersOfTheDayByState(OrderStatus.ACCEPTED))

        return orders
    }

    private fun getOrder(orderId: UUID): Order {
        return orderRepository.findById(orderId)
            .orElseThrow { throw DomainException("Order not found") }
    }

    private fun verifyProduct(product: Product) {
        val productDTO = productService.findProductById(product.id) ?: throw DomainException("Product not found")
        if (productDTO.status == false) {
            throw DomainException("Product is inactive")
        }
    }
}