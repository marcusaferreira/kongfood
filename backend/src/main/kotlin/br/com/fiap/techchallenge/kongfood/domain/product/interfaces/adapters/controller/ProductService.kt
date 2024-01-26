package br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.controller

import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductRequestModel
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductResponseModel
import java.util.UUID

interface ProductService {
    fun addProduct(product: ProductRequestModel): String

    fun updateProduct(product: ProductRequestModel): ProductResponseModel

    fun changeStatus(productID: UUID)

    fun findProductById(productID: UUID): ProductResponseModel

    fun findProductByCategory(category: String): List<ProductResponseModel>
    fun findAll(): List<ProductResponseModel>

}