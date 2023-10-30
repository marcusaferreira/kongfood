package br.com.fiap.techchallenge.kongfood.domain.product.service.dto

import br.com.fiap.techchallenge.kongfood.domain.product.Product
import br.com.fiap.techchallenge.kongfood.domain.product.ProductCategory
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.math.BigDecimal
import java.util.*

data class ProductDTO(
    var id: String?,
    @field:NotBlank(message = "Price cannot be blank")
    @field:Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?\$", message = "Price must be only numbers and 2 decimal places")
    var price: String,
    @field:NotBlank(message = "Name cannot be blank")
    var name: String,
    @field:NotBlank(message = "Description cannot be blank")
    var description: String,
    @field:NotBlank(message = "Category cannot be blank")
    var category: String,
    var status: Boolean? = true,
) {
    fun toEntity(): Product {
        return Product(
            id = if (!id.isNullOrBlank()) UUID.fromString(id) else UUID.randomUUID(),
            price = BigDecimal(price),
            name = name,
            description = description,
            category = ProductCategory.getEnumByString(category)!!,
            status = status!!
        )
    }

    companion object {
        fun convertFromEntityToDTO(product: Product): ProductDTO {
            return ProductDTO(
                product.id.toString(),
                product.price.toString(),
                product.name,
                product.description,
                product.category.type,
                product.status
            )
        }
    }

}
