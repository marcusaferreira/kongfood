package br.com.fiap.techchallenge.kongfood.infrastructure.repository

import br.com.fiap.techchallenge.kongfood.domain.product.Product
import br.com.fiap.techchallenge.kongfood.domain.product.ProductCategory
import br.com.fiap.techchallenge.kongfood.domain.product.repository.ProductRepository
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

}