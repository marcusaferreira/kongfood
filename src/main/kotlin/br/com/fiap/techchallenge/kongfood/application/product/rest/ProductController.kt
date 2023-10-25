package br.com.fiap.techchallenge.kongfood.application.product.rest

import br.com.fiap.techchallenge.kongfood.domain.product.service.dto.ProductDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.*

@RestController
@RequestMapping("/products")
class ProductController {

    @Operation(summary = "Find product by ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Product found",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ProductDTO::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Product not found",
                ),
        ]
    )
    @GetMapping("/{id}")
    fun findById(@Parameter(description = "id of the Poduct to be searched")@PathVariable id: String): ProductDTO? {
        return null
    }

    @GetMapping("/category/{category}")
    fun findByCategory(@Parameter(description = "category of the Poducts to be searched")@PathVariable category: String): List<ProductDTO> {
        return listOf(ProductDTO(UUID.randomUUID(), BigDecimal.valueOf(10), "Coca", "Refrigerante tipo cola", "Drinks", true),
            ProductDTO(UUID.randomUUID(), BigDecimal.valueOf(9), "Guaraná", "Refrigerante tipo guaraná", "Drinks", true),
            )
    }
}