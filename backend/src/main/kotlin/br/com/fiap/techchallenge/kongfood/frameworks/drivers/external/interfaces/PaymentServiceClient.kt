package br.com.fiap.techchallenge.kongfood.frameworks.drivers.external.interfaces

import br.com.fiap.techchallenge.kongfood.frameworks.drivers.external.interfaces.dto.PaymentClientRequest
import br.com.fiap.techchallenge.kongfood.frameworks.drivers.external.interfaces.dto.PaymentClientResponse
import org.springframework.http.ResponseEntity

interface PaymentServiceClient {

    fun payment(paymentRequest: PaymentClientRequest): ResponseEntity<PaymentClientResponse>

    fun paymentInfo(paymentID: String): ResponseEntity<PaymentClientResponse>

}