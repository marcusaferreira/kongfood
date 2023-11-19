package br.com.fiap.techchallenge.kongfood.domain.product

import br.com.fiap.techchallenge.kongfood.domain.product.service.dto.ProductDTO
import java.util.*

internal class ProductProvider {

    companion object{
        fun getProductDTO() = ProductDTO(
            id = null,
            name = "Hamburguer",
            description = "A delicious hamburguer",
            price = "10.0",
            category = "Main courses",
            status = true
        )

        fun getProduct(): Product {
            return Product(
                id = UUID.randomUUID(),
                name = "Hamburguer",
                description = "A delicious hamburguer",
                price = 10.0.toBigDecimal(),
                category = ProductCategory.MAIN_COURSES,
                status = true
            )

        }
    }
}