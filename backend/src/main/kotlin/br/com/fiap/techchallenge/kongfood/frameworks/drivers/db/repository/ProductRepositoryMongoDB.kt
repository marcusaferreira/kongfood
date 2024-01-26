package br.com.fiap.techchallenge.kongfood.frameworks.drivers.db.repository

import br.com.fiap.techchallenge.kongfood.domain.product.entities.Product
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductCategory
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.repository.ProductRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.util.*

@Component
@Primary
class ProductRepositoryMongoDB(
    val productRepository: ProductRepositorySpringDataMongo
) : ProductRepository {
    override fun save(product: Product) {
        productRepository.save(product)
    }

    override fun findById(id: UUID): Optional<Product> {
        return productRepository.findById(id)
    }

    override fun findByCategory(productCategory: ProductCategory): List<Product> {
        return productRepository.findByCategory(productCategory)
    }

    override fun findByName(name: String): Product? {
        return productRepository.findByName(name)
    }

    override fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    override fun existsByName(product: String): Boolean {
        val productExist = productRepository.findByName(product)
        return productExist != null
    }

    override fun exists(product: Product): Boolean {
        val productExist = productRepository.findByName(product.name)
        return productExist != null && productExist.id != product.id
    }

}