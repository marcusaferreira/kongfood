package br.com.fiap.techchallenge.kongfood.domain.order

import br.com.fiap.techchallenge.kongfood.domain.product.ProductCategory
import java.math.BigDecimal
import java.util.*

class Product(
    val id: UUID,
    val price: BigDecimal,
    val name: String,
    val description: String,
    val category: ProductCategory,
) {

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