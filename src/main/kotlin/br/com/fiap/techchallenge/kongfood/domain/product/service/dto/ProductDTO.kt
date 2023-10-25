package br.com.fiap.techchallenge.kongfood.domain.product.service.dto

import br.com.fiap.techchallenge.kongfood.domain.product.Product
import br.com.fiap.techchallenge.kongfood.domain.product.ProductCategory
import java.math.BigDecimal
import java.util.*

data class ProductDTO(
    val id: UUID?,
    val price: BigDecimal,
    val name: String,
    val description: String,
    val category: String,
    val status: Boolean
) {
    fun toEntity(): Product {
        return Product(
            id = id ?: UUID.randomUUID(),
            price = price,
            name = name,
            description = description,
            category = ProductCategory.valueOf(category),
            status = status
        )
    }

    companion object {
        fun convertFromEntityToDTO(product: Product): ProductDTO {
            return ProductDTO(
                product.id,
                product.price,
                product.name,
                product.description,
                product.category.type,
                product.status
            )
        }
    }

}
