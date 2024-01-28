package br.com.fiap.techchallenge.kongfood.frameworks.drivers.ui

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.controllers.CustomerService
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerRequestModel
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.net.URI

@RestController
@RequestMapping("/customers")
class CustomerRestController(
    val customerService: CustomerService
) {

    @Operation(summary = "Find customer by CPF")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Customer found",
                content = [io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = io.swagger.v3.oas.annotations.media.Schema(implementation = CustomerRequestModel::class))]
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Customer not found",
            ),
        ]
    )
    @GetMapping("/cpf/{cpf}")
    fun findByCpf(@PathVariable cpf: String): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(customerService.findCustomerByCpf(cpf))
        } catch (e: DomainException) {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Find customer by email")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Customer found",
                content = [io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = io.swagger.v3.oas.annotations.media.Schema(implementation = CustomerRequestModel::class))]
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Customer not found",
            ),
        ]
    )
    @GetMapping("/email/{email}")
    fun findByEmail(@PathVariable email: String): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(customerService.findCustomerByEmail(email))
        } catch (e: DomainException) {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Create customer")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Customer created",
                content = [io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = io.swagger.v3.oas.annotations.media.Schema(implementation = CustomerRequestModel::class))]
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "422",
                description = "Unprocessable entity",
            ),
        ]
    )
    @PostMapping
    fun create(@RequestBody @Valid customerRequestModel: CustomerRequestModel): ResponseEntity<Any> {
        return try {
            val id = customerService.createCustomer(customerRequestModel)
            ResponseEntity.created(URI.create("/customers/$id")).body(id)
        } catch (e: DomainException) {
            ResponseEntity.unprocessableEntity().body(e.message)
        } catch (e: IllegalArgumentException){
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @Operation(summary = "Find customer by id")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Customer found",
                content = [io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")]
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Customer not found",
            ),
        ]
    )
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(customerService.findCustomerById(id))
        } catch (e: DomainException) {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Update customer")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Customer updated",
                content = [io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = io.swagger.v3.oas.annotations.media.Schema(implementation = CustomerRequestModel::class)
                )]
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Bad request",
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Customer not found",
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "422",
                description = "Unprocessable entity",
            ),
        ]
    )
    @PutMapping("/{id}")
    fun update(@RequestBody @Valid customerRequestModel: CustomerRequestModel, @PathVariable id: String): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(customerService.updateCustomer(customerRequestModel, id))
        } catch (e: DomainException) {
            when (e.message) {
                "Customer not found for the id $id" -> ResponseEntity.notFound().build()
                "Customer id cannot be changed" -> ResponseEntity.unprocessableEntity().body(e.message)
                "CPF cannot be changed" -> ResponseEntity.unprocessableEntity().body(e.message)
                "Customer is not active" -> ResponseEntity.unprocessableEntity().body(e.message)
                else -> ResponseEntity.badRequest().build()
            }
        }
    }

    @Operation(summary = "Deactivate customer")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Customer deactivated",
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Bad request",
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Customer not found",
            ),
        ]
    )
    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable id: String): ResponseEntity<Any> {
        return try {
            customerService.deActivateCustomer(id)
            ResponseEntity.ok().build()
        } catch (e: DomainException) {
            when (e.message) {
                "Customer not found for the id $id" -> ResponseEntity.notFound().build()
                else -> ResponseEntity.badRequest().build()
            }
        }
    }

    @Operation(summary = "Activate customer")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Customer deactivated",
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Bad request",
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Customer not found",

                ),
        ]
    )
    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable id: String): ResponseEntity<Any> {
        return try {
            customerService.deActivateCustomer(id)
            ResponseEntity.ok().build()
        } catch (e: DomainException) {
            when (e.message) {
                "Customer not found for the id $id" -> ResponseEntity.notFound().build()
                else -> ResponseEntity.badRequest().build()
            }
        }
    }
}