package br.com.fiap.techchallenge.kongfood.application.rest

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.order.OrderStatus
import br.com.fiap.techchallenge.kongfood.domain.order.service.OrderService
import br.com.fiap.techchallenge.kongfood.domain.order.service.dto.OrderDTO
import br.com.fiap.techchallenge.kongfood.domain.order.service.dto.OrderLineDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

private const val ORDER_NOT_FOUND = "Order not found"
private const val ORDER_MUST_NOT_FINISHED_OR_CANCELED = "Order must not be FINISHED or CANCELED to change status"

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
                content = [io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = io.swagger.v3.oas.annotations.media.Schema(implementation = UUID::class)
                )]
            ),
        ]
    )
    @PostMapping
    fun createOrder(
        @Parameter(required = false, description = "ID of the customer") @RequestParam(
            required = false, value = "customerId"
        ) customerId: String?
    ): ResponseEntity<Any> {
        return try {
            val orderId = orderService.createOrder(if(!customerId.isNullOrBlank())UUID.fromString(customerId) else null)
            ResponseEntity.created(URI.create("/orders/$orderId")).body(orderId)
        } catch (e: DomainException) {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Add product to order")
    @ApiResponses(
        value = [io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Product added to order"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", description = "Bad request"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = ORDER_NOT_FOUND
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "422", description = "Product not found or Product is inactive or " +
                    "Order must not be FINISHED, CANCELED or READY to add or remove order line"
        )]
    )
    @PostMapping("/{id}/lines")
    fun addOrderLine(
        @Parameter(description = "ID of the order") @PathVariable("id", required = true) id: UUID,
        @Parameter(description = "Product to be removed") @RequestBody orderLine: OrderLineDTO
    ): ResponseEntity<Any> {
        return try {
            orderService.addOrderLine(id, orderLine)
            ResponseEntity.ok(orderService.getOrderData(id))
        } catch (e: DomainException) {
            when (e.message) {
                "Product not found", "Order must not be FINISHED, CANCELED or READY to add or remove order line",
                "Product is inactive", "Product not founded for the ID ${orderLine.productId}"  -> ResponseEntity.unprocessableEntity().body(e.message)
                ORDER_NOT_FOUND -> ResponseEntity.notFound().build()
                else -> ResponseEntity.badRequest().build()
            }
        }
    }

    @Operation(summary = "Remove product from order")
    @ApiResponses(
        value = [io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Product removed from order"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", description = "Bad request"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = ORDER_NOT_FOUND
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "422", description = "Product not found or Product is inactive or " +
                    "Order must not be FINISHED, CANCELED or READY to add or remove order line"
        )]
    )
    @DeleteMapping("/{id}/lines")
    fun removeOrderLine(
        @Parameter(description = "ID of the order") @PathVariable("id", required = true) id: UUID,
        @Parameter(description = "Product to be removed") @RequestBody orderLine: OrderLineDTO
    ): ResponseEntity<Any> {
        return try {
            orderService.removeOrderLine(id, orderLine)
            ResponseEntity.ok().build()
        } catch (e: DomainException) {
            when (e.message) {
                "Product not found", "Order must not be FINISHED, CANCELED or READY to add or remove order line",
                "Product is inactive"  -> ResponseEntity.unprocessableEntity().body(e.message)
                ORDER_NOT_FOUND -> ResponseEntity.notFound().build()
                else -> ResponseEntity.badRequest().build()
            }
        }
    }

    @Operation(summary = "Confirm order")
    @ApiResponses(
        value = [io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Order confirmed"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", description = "Bad request"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = ORDER_NOT_FOUND
        )]
    )
    @PatchMapping("/{id}/confirm")
    fun confirmOrder(
        @Parameter(description = "ID of the order", required = true) @PathVariable("id", required = true) id: String
    ): ResponseEntity<Any> {
        return try {
            orderService.confirmOrder(UUID.fromString(id))
            ResponseEntity.ok(orderService.getOrderData(UUID.fromString(id)))
        } catch (e: DomainException) {
            when (e.message) {
                ORDER_NOT_FOUND -> ResponseEntity.notFound().build()
                ORDER_MUST_NOT_FINISHED_OR_CANCELED -> ResponseEntity.unprocessableEntity().body(e.message)
                else -> ResponseEntity.badRequest().build()
            }
        }
    }

    @Operation(summary = "Prepare order")
    @ApiResponses(
        value = [io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Order prepared"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", description = "Bad request"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = ORDER_NOT_FOUND
        )]
    )
    @PatchMapping("/{id}/prepare")
    fun prepareOrder(
        @Parameter(description = "ID of the order", required = true) @PathVariable("id", required = true) id: String
    ): ResponseEntity<Any> {
        return try {
            orderService.prepareOrder(UUID.fromString(id))
            ResponseEntity.ok(orderService.getOrderData(UUID.fromString(id)))
        } catch (e: DomainException) {
            when (e.message) {
                ORDER_NOT_FOUND -> ResponseEntity.notFound().build()
                ORDER_MUST_NOT_FINISHED_OR_CANCELED -> ResponseEntity.unprocessableEntity().body(e.message)
                else -> ResponseEntity.badRequest().build()
            }
        }
    }

    @Operation(summary = "Notify prepared order")
    @ApiResponses(
        value = [io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Order notified as prepared"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", description = "Bad request"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = ORDER_NOT_FOUND
        )]
    )
    @PatchMapping("/{id}/notify")
    fun notifyPreparedOrder(
        @Parameter(description = "ID of the order", required = true) @PathVariable("id", required = true) id: String
    ): ResponseEntity<Any> {
        return try {
            orderService.notifyPreparedOrder(UUID.fromString(id))
            ResponseEntity.ok(orderService.getOrderData(UUID.fromString(id)))
        } catch (e: DomainException) {
            when (e.message) {
                ORDER_NOT_FOUND -> ResponseEntity.notFound().build()
                ORDER_MUST_NOT_FINISHED_OR_CANCELED -> ResponseEntity.unprocessableEntity().body(e.message)
                else -> ResponseEntity.badRequest().build()
            }
        }
    }

    @Operation(summary = "Cancel order")
    @ApiResponses(
        value = [io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Order canceled"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", description = "Bad request"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = ORDER_NOT_FOUND
        )]
    )
    @PatchMapping("/{id}/cancel")
    fun cancelOrder(
        @Parameter(description = "ID of the order", required = true) @PathVariable("id", required = true) id: String
    ): ResponseEntity<Any> {
        return try {
            orderService.cancelOrder(UUID.fromString(id))
            ResponseEntity.ok(orderService.getOrderData(UUID.fromString(id)))
        } catch (e: DomainException) {
            when (e.message) {
                ORDER_NOT_FOUND -> ResponseEntity.notFound().build()
                "Order must not be FINISHED or READY to cancel" -> ResponseEntity.unprocessableEntity().body(e.message)
                else -> ResponseEntity.badRequest().build()
            }
        }
    }

    @Operation(summary = "Finish order")
    @ApiResponses(
        value = [io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Order finished"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", description = "Bad request"
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = ORDER_NOT_FOUND
        )]
    )
    @PatchMapping("/{id}/finish")
    fun finishOrder(
        @Parameter(description = "ID of the order", required = true) @PathVariable("id", required = true) id: String
    ): ResponseEntity<Any> {
        return try {
            orderService.finishOrder(UUID.fromString(id))
            ResponseEntity.ok(orderService.getOrderData(UUID.fromString(id)))
        } catch (e: DomainException) {
            when (e.message) {
                ORDER_NOT_FOUND -> ResponseEntity.notFound().build()
                "Order must be READY to complete" -> ResponseEntity.unprocessableEntity().body(e.message)
                else -> ResponseEntity.badRequest().build()
            }
        }
    }

    @Operation(summary = "Get order data")
    @ApiResponses(
        value = [io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Order data", content = [io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = io.swagger.v3.oas.annotations.media.Schema(implementation = OrderDTO::class)
            )]
        ), io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = ORDER_NOT_FOUND
        )]
    )
    @GetMapping("/{id}")
    fun getOrderData(
        @Parameter(description = "ID of the order", required = true) @PathVariable("id", required = true) id: String
    ): ResponseEntity<Any> {
        return try {
            val order = orderService.getOrderData(UUID.fromString(id))
            ResponseEntity.ok().body(order)
        } catch (e: DomainException) {
            when (e.message) {
                ORDER_NOT_FOUND -> ResponseEntity.notFound().build()
                else -> ResponseEntity.badRequest().build()
            }
        }
    }

    @Operation(summary = "List orders of the day by state")
    @ApiResponses(
        value = [io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "List of orders of the day by state", content = [io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = io.swagger.v3.oas.annotations.media.Schema(implementation = OrderDTO::class)
            )]
        )]
    )
    @GetMapping("/orders-of-the-day")
    fun listOrdersOfTheDayByState(
        @Parameter(description = "State of the order", required = true,
            schema = Schema(implementation = OrderStatus::class),
            examples = [io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "status", value = "CREATED", summary = "CREATED"
            ), io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "status", value = "PENDING", summary = "PENDING"
            ), io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "status", value = "ACCEPTED", summary = "ACCEPTED"
            ), io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "status", value = "IN_PREPARATION", summary = "IN_PREPARATION"
            ), io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "status", value = "READY", summary = "READY"
            ), io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "status", value = "FINISHED", summary = "FINISHED"
            ), io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "status", value = "CANCELED", summary = "CANCELED"
            )])
        @RequestParam("status", required = true) status: OrderStatus
    ): ResponseEntity<Any> {
        return try {
            val order = orderService.listOrdersOfTheDayByState(status)
            ResponseEntity.ok().body(order)
        } catch (e: DomainException) {
            ResponseEntity.badRequest().build()
        }
    }

}