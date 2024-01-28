package br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.controllers

import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderLineRequestModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderPresenter
import br.com.fiap.techchallenge.kongfood.domain.order.usecases.*
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.controllers.ProductService
import java.util.*

class DomainOrderService(
    val productService: ProductService,
    val orderSearchBoundary: OrderSearchBoundary,
    val orderRegisterBoundary: OrderRegisterBoundary,
    val orderAddProductBoundary: OrderAddProductBoundary,
    val orderPresenter: OrderPresenter,
    val orderRemoveProductBoundary: OrderRemoveProductBoundary,
    val orderChangeStateBoundary: OrderChangeStateBoundary
) : OrderService {

    override fun createOrder(customerId: UUID?): UUID {
        val order = orderRegisterBoundary.create(customerId)
        return order.id
    }

    override fun addOrderLine(orderId: UUID, orderLine: OrderLineRequestModel) {
        verifyProduct(orderLine.productId)
        orderAddProductBoundary.addOrderLine(orderId, orderLine)
    }

    override fun removeOrderLine(orderId: UUID, orderLine: OrderLineRequestModel) {
        verifyProduct(orderLine.productId)
        orderRemoveProductBoundary.removeOrderLine(orderId, orderLine)
    }

    override fun confirmOrder(orderId: UUID) {
        orderChangeStateBoundary.changeState(orderId, OrderStatus.ACCEPTED)
    }

    override fun prepareOrder(orderId: UUID) {
        orderChangeStateBoundary.changeState(orderId, OrderStatus.IN_PREPARATION)
    }

    override fun notifyPreparedOrder(orderId: UUID) {
        orderChangeStateBoundary.changeState(orderId, OrderStatus.READY)
    }

    override fun cancelOrder(orderId: UUID) {
        orderChangeStateBoundary.cancelOrder(orderId)
    }

    override fun finishOrder(orderId: UUID) {
        orderChangeStateBoundary.finishOrder(orderId)
    }

    override fun getOrderData(orderId: UUID): OrderResponseModel {
        return orderSearchBoundary.getOrderData(orderId)
    }

    override fun listOrdersOfTheDayByState(status: OrderStatus): List<OrderResponseModel> {
        return orderSearchBoundary.listOrdersOfTheDayByState(status)
    }

    override fun listPriorityOrdersOfTheDay(): List<OrderResponseModel> {
        return orderSearchBoundary.listPriorityOrdersOfTheDay()
    }

    private fun verifyProduct(productID: UUID) {
        val productDTO = productService.findProductById(productID)
        if (productDTO.status == false) {
            orderPresenter.prepareFailureResponse("Product is inactive")
        }
    }
}