package br.com.fiap.techchallenge.kongfood.application.rest

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.customer.service.CustomerService
import br.com.fiap.techchallenge.kongfood.domain.customer.service.dto.CustomerDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(
    val customerService: CustomerService
) {

    @Operation(summary = "Find customer by CPF")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Customer found",
                content = [io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = io.swagger.v3.oas.annotations.media.Schema(implementation = CustomerDTO::class))]
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
                    schema = io.swagger.v3.oas.annotations.media.Schema(implementation = CustomerDTO::class))]
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
                content = [io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = io.swagger.v3.oas.annotations.media.Schema(implementation = CustomerDTO::class))]
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "422",
                description = "Unprocessable entity",
            ),
        ]
    )
    @PostMapping
    fun create(@RequestBody @Valid customerDTO: CustomerDTO): ResponseEntity<Any> {
        return try {
            val id = customerService.createCustomer(customerDTO)
            ResponseEntity.created(java.net.URI.create("/customers/$id")).build()
        } catch (e: DomainException) {
            ResponseEntity.unprocessableEntity().body(e.message)
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
                    schema = io.swagger.v3.oas.annotations.media.Schema(implementation = CustomerDTO::class)
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
    fun update(@RequestBody @Valid customerDTO: CustomerDTO, @PathVariable id: String): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(customerService.updateCustomer(customerDTO, id))
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