package br.com.fiap.techchallenge.kongfood.domain.product.entities

import java.util.*

class ProductFactoryImplementation : ProductFactory {
    override fun create(productID: UUID?, name: String, description: String, price: String, category: String): Product {
        return Product(
            productID ?: UUID.randomUUID(),
            price.toBigDecimal(),
            name,
            description,
            ProductCategory.getEnumByString(category)!!,
            true
        )
    }
}