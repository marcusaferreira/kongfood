package br.com.fiap.techchallenge.kongfood.domain.product

import java.math.BigDecimal
import java.util.*

class Product(
    val id: UUID,
    val price: BigDecimal,
    val name: String,
    val description: String,
    val category: ProductCategory,
    val status: Boolean
) {
    constructor(product: Product, newStatus: Boolean) :
            this(product.id, product.price, product.name, product.description, product.category, newStatus)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Product) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}