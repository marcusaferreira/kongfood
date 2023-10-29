package br.com.fiap.techchallenge.kongfood.domain.product.repository

import br.com.fiap.techchallenge.kongfood.domain.product.Product
import br.com.fiap.techchallenge.kongfood.domain.product.ProductCategory
import java.util.*

interface ProductRepository {
    fun save(product: Product)
    fun findById(id: UUID): Optional<Product>
    fun findByCategory(productCategory: ProductCategory): List<Product>
    fun findByName(name: String): Product?
    fun findAll(): List<Product>
}