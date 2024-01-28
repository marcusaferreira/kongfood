package br.com.fiap.techchallenge.kongfood.domain.order.usecases

import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderFactory
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderResponseModel
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderPresenter
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.repository.OrderRepository
import java.util.*

class OrderRegisterInteractor(
    val orderRepository: OrderRepository,
    val orderPresenter: OrderPresenter,
    val orderFactory: OrderFactory
) : OrderRegisterBoundary{
    override fun create(customerId: UUID?): OrderResponseModel{
        val order = orderFactory.createOrder(customerId)
        val lastOrderNumber = orderRepository.countOrdersOfTheDay()
        order.generateTrackOrderCode(lastOrderNumber)
        orderRepository.save(order)

        return orderPresenter.prepareSuccessResponse(order)

    }
}