package br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.repository

import br.com.fiap.techchallenge.kongfood.domain.product.entities.Product
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductCategory
import java.util.*

interface ProductRepository {
    fun save(product: Product)
    fun findById(id: UUID): Optional<Product>
    fun findByCategory(productCategory: ProductCategory): List<Product>
    fun findByName(name: String): Product?
    fun findAll(): List<Product>
    fun existsByName(product: String): Boolean
    fun exists(product: Product): Boolean
}