package br.com.fiap.techchallenge.kongfood.domain.product

import br.com.fiap.techchallenge.kongfood.domain.product.entities.Product
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductCategory
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductRequestModel
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductResponseModel
import java.util.*

internal class ProductProvider {

    companion object{
        fun getProductDTO() = ProductRequestModel(
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

        fun getProductResponse(): ProductResponseModel {
            return ProductResponseModel(
                id = UUID.randomUUID().toString(),
                name = "Hamburguer",
                description = "A delicious hamburguer",
                price = "10.0",
                category = ProductCategory.MAIN_COURSES.type,
                status = true
            )
        }
    }
}