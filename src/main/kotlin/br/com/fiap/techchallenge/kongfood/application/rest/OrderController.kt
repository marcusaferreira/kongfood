package br.com.fiap.techchallenge.kongfood.application.rest

import br.com.fiap.techchallenge.kongfood.domain.order.service.OrderService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(
    val orderService: OrderService
) {

}