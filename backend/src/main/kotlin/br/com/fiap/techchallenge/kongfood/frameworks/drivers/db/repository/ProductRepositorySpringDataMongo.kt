package br.com.fiap.techchallenge.kongfood.frameworks.drivers.db.repository

import br.com.fiap.techchallenge.kongfood.domain.product.entities.Product
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductCategory
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepositorySpringDataMongo: MongoRepository<Product, UUID> {
    fun findByCategory(productCategory: ProductCategory): List<Product>

    fun findByName(name: String): Product?

}
