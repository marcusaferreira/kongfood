package br.com.fiap.techchallenge.kongfood.frameworks.drivers.ui

import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.controllers.PaymentService
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.models.PaymentResponse
import br.com.fiap.techchallenge.kongfood.frameworks.drivers.ui.dto.PaymentNotification
import io.swagger.v3.oas.annotations.Operation
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

    @Operation(summary = "Notify the status of a payment, this is for getting the return from the payment gateway")
    @PostMapping("/notify/{orderID}")
    fun notify(@PathVariable("orderID") orderID: String, @RequestBody payment: PaymentNotification) {
        paymentService.notify(orderID, payment)
    }

    @Operation(summary = "Request a payment to the payment gateway. This is temporary, in the future this will be done by the payment gateway")
    @PostMapping("/request/{orderID}")
    fun requestPayment(@PathVariable("orderID") orderID: String): PaymentResponse {
        return paymentService.request(orderID)
    }

    @Operation(summary = "Verify the status of a payment, this is for getting the return from the payment gateway")
    @GetMapping("/verify/{orderID}")
    fun verifyPayment(@PathVariable("orderID") orderID: String): PaymentResponse {
        return paymentService.verify(orderID)
    }
}