package br.com.fiap.techchallenge.kongfood.application.order.service

import br.com.fiap.techchallenge.kongfood.domain.order.service.OrderService
import org.springframework.stereotype.Service

@Service
class ApplicationOrderService(
    val orderService: OrderService
) {
}