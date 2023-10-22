package br.com.fiap.techchallenge.kongfood.application.order.rest

import br.com.fiap.techchallenge.kongfood.application.order.service.ApplicationOrderService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(
    val orderService: ApplicationOrderService
) {

}