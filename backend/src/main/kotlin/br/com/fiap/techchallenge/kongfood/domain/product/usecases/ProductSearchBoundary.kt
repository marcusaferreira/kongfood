package br.com.fiap.techchallenge.kongfood.domain.product.usecases

import br.com.fiap.techchallenge.kongfood.domain.product.entities.Product
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductResponseModel
import java.util.*

interface ProductSearchBoundary {

    fun validateExists(product: Optional<Product>, productID: UUID)

    fun findById(productID: UUID): ProductResponseModel

    fun findByCategory(category: String): List<ProductResponseModel>

    fun findAll(): List<ProductResponseModel>

}
