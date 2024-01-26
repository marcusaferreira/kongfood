package br.com.fiap.techchallenge.kongfood.domain.product.usecases

import br.com.fiap.techchallenge.kongfood.domain.product.entities.Product
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductResponseModel
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.presenters.ProductPresenter
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.repository.ProductRepository
import java.util.*

class ProductChangeStatusInteractor(
    val productRepository: ProductRepository,
    val productPresenter: ProductPresenter,
    val productSearchBoundary: ProductSearchBoundary
): ProductChangeStatusBoundary {
    override fun changeStatus(productID: UUID): ProductResponseModel {
        val product = productRepository.findById(productID)
        productSearchBoundary.validateExists(product, productID)
        val productChanged = Product(product.get(), !product.get().status)
        productRepository.save(productChanged)
        return productPresenter.prepareSuccessResponse(productChanged)
    }

}
