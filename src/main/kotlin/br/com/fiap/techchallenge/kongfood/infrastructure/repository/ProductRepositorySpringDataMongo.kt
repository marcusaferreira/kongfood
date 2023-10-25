package br.com.fiap.techchallenge.kongfood.infrastructure.repository

import br.com.fiap.techchallenge.kongfood.domain.product.Product
import br.com.fiap.techchallenge.kongfood.domain.product.ProductCategory
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepositorySpringDataMongo: MongoRepository<Product, UUID> {
    fun findByCategory(productCategory: ProductCategory): List<Product>

}
