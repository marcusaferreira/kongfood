package br.com.fiap.techchallenge.kongfood.frameworks.drivers.ui

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.controllers.ProductService
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductRequestModel
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/products")
class ProductRestController(
    val productService: ProductService
) {

    @Operation(summary = "Find product by ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Product found",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ProductRequestModel::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Product not found",
            ),
        ]
    )
    @GetMapping("/{id}")
    fun findById(
        @Parameter(
            description = "id of the Poduct to be searched", required = true
        ) @PathVariable id: String
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(productService.findProductById(UUID.fromString(id)))
        } catch (e: DomainException) {
            ResponseEntity.notFound().build()
        }
    }


    @Operation(summary = "Find products by category")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Product found",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ProductRequestModel::class))]
            ),
            ApiResponse(
                responseCode = "422",
                description = "Category not found",
            ),
        ]
    )
    @GetMapping("/category/{category}")
    fun findByCategory(
        @Parameter(
            description = "category of the Poducts to be searched",
            required = true,
            schema = Schema(implementation = String::class),
            examples = [ExampleObject(value = "Drinks"),
                ExampleObject(value = "Desserts"),
                ExampleObject(value = "Main courses"),
                ExampleObject(value = "Side dishes"),
                ExampleObject(value = "Combos")]

        ) @PathVariable category: String
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(productService.findProductByCategory(category))
        } catch (e: DomainException) {
            when (e.message) {
                "Category not found" -> {
                    ResponseEntity.unprocessableEntity().body(e.message + " - " + category)
                }

                else -> ResponseEntity.badRequest().body("Error")
            }
        }
    }

    @Operation(summary = "Add a new product")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Product added",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = UUID::class))]
            ),
            ApiResponse(
                responseCode = "422",
                description = "Product already exists",
            ),
        ]
    )
    @PostMapping
    fun addProduct(@RequestBody @Valid product: ProductRequestModel): ResponseEntity<Any> {
        return try {
            val productID = productService.addProduct(product)
            ResponseEntity.created(URI("/products/$productID")).body(productID)
        } catch (e: DomainException) {
            when (e.message) {
                "Product already exists" -> {
                    ResponseEntity.unprocessableEntity().body(e.message)
                }

                else -> ResponseEntity.badRequest().body("Error")
            }
        }
    }

    @Operation(summary = "Update a product")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Product updated",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ProductRequestModel::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Product not found",
            ),
            ApiResponse(
                responseCode = "422",
                description = "Product already exists",
            ),
            ApiResponse(
                responseCode = "422",
                description = "Product category can't be changed",
            ),
        ]
    )
    @PutMapping
    fun updateProduct(@RequestBody @Valid product: ProductRequestModel): ResponseEntity<Any> {
        return try {
            val productUpdated = productService.updateProduct(product)

            ResponseEntity.ok(productUpdated)
        } catch (e: DomainException) {
            when (e.message) {
                "Product not founded for the ID ${product.id}" -> {
                    ResponseEntity.notFound().build()
                }

                "Product already exists" -> {
                    ResponseEntity.unprocessableEntity().body(e.message)
                }

                "Product category can't be changed" -> {
                    ResponseEntity.unprocessableEntity().body(e.message)
                }

                else -> ResponseEntity.badRequest().body("Error")
            }
        }

    }

    @Operation(summary = "Change product status")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "Product status changed",
            content = [Content(mediaType = "application/json")]
        ), ApiResponse(
            responseCode = "404",
            description = "Product not found",
        )]
    )
    @PatchMapping("/{id}/status")
    fun changeStatus(
        @Parameter(
            description = "id of the Poduct to be changed", required = true
        ) @PathVariable id: String
    ): ResponseEntity<Unit> {
        return try {
            productService.changeStatus(UUID.fromString(id))
            ResponseEntity.ok().build()
        } catch (e: DomainException) {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Find all products")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Products found",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ProductRequestModel::class))]
            ),
        ]
    )
    @GetMapping
    fun findAll(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(productService.findAll())
        } catch (e: DomainException) {
            ResponseEntity.notFound().build()
        }
    }
}