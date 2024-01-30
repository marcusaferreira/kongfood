package br.com.fiap.techchallenge.kongfood.frameworks.drivers.ui

import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.controllers.PaymentService
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse
import br.com.fiap.techchallenge.kongfood.frameworks.drivers.ui.dto.PaymentNotification
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payments")
class PaymentsRestController(
    val paymentService: PaymentService
) {

    @PostMapping("/notify/{orderID}")
    fun notify(@PathVariable("orderID") orderID: String, @RequestBody payment: PaymentNotification) {
        paymentService.notify(orderID, payment)
    }

    @PostMapping("/request/{orderID}")
    fun requestPayment(@PathVariable("orderID") orderID: String): PaymentResponse {
        return paymentService.request(orderID)
    }

    @GetMapping("/verify/{orderID}")
    fun verifyPayment(@PathVariable("orderID") orderID: String): PaymentResponse {
        return paymentService.verify(orderID)
    }
}