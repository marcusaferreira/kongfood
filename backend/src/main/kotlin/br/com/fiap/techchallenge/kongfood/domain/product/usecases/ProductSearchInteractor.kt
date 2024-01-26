package br.com.fiap.techchallenge.kongfood.domain.product.usecases

import br.com.fiap.techchallenge.kongfood.domain.product.entities.Product
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductCategory
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductResponseModel
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.presenters.ProductPresenter
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.repository.ProductRepository
import java.util.*

class ProductSearchInteractor (
    val productRepository: ProductRepository,
    val productPresenter: ProductPresenter
) : ProductSearchBoundary {

    override fun validateExists(product: Optional<Product>, productID: UUID){
        if (product.isEmpty) {
            productPresenter.prepareFailureResponse("Product not founded for the ID $productID")
        }
    }

    override fun findById(productID: UUID): ProductResponseModel{
        val product = productRepository.findById(productID)
        validateExists(product, productID)
        return productPresenter.prepareSuccessResponse(product.get())
    }

    override fun findByCategory(category: String): List<ProductResponseModel>{
        val productCategory = ProductCategory.getEnumByString(category)
        if (productCategory == null) {
            productPresenter.prepareFailureResponse("Category not found")
        }
        val products = productRepository.findByCategory(productCategory!!)

        return productPresenter.prepareAllSuccessResponse(products)
    }


    override fun findAll(): List<ProductResponseModel>{
        return productRepository.findAll().map { productPresenter.prepareSuccessResponse(it) }
    }

}
