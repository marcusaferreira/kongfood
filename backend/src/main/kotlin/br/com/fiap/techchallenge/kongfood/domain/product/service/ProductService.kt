package br.com.fiap.techchallenge.kongfood.domain.product.service

import br.com.fiap.techchallenge.kongfood.domain.product.service.dto.ProductDTO
import java.util.UUID

interface ProductService {
    fun addProduct(product: ProductDTO): UUID

    fun updateProduct(product: ProductDTO): ProductDTO

    fun changeStatus(productID: UUID)

    fun findProductById(productID: UUID): ProductDTO?

    fun findProductByCategory(category: String): List<ProductDTO>

}