package br.com.fiap.techchallenge.kongfood.application.rest

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.order.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/orders")
class OrderController(
    val orderService: OrderService
) {

    @Operation(summary = "Create order")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Order created",
                content = [io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = io.swagger.v3.oas.annotations.media.Schema(implementation = UUID::class))]
            ),
        ]
    )
    @PostMapping
    fun createOrder(string: String): ResponseEntity<Any> {
        return try {
            val orderId = orderService.createOrder(UUID.fromString(string))
            ResponseEntity.created(URI.create("/orders/$orderId")).body(orderId)
        } catch (e: DomainException) {
            ResponseEntity.notFound().build()
        }

    }

}