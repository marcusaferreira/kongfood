package br.com.fiap.techchallenge.kongfood.domain.product.usecases

import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductFactory
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductRequestModel
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.repository.ProductRepository
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductResponseModel
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.presenters.ProductPresenter

class ProductRegisterInteractor(
    val productRepository: ProductRepository,
    val productFactory: ProductFactory,
    val productPresenter: ProductPresenter
) : ProductRegisterBoundary {

    override fun registerProduct(productRequestModel: ProductRequestModel): ProductResponseModel {
        if(productRepository.existsByName(productRequestModel.name)){
            productPresenter.prepareFailureResponse("Product already exists")
        }

        val newProduct = productFactory.create(
            null,
            productRequestModel.name,
            productRequestModel.description,
            productRequestModel.price,
            productRequestModel.category
        )
        productRepository.save(newProduct)
        return productPresenter.prepareSuccessResponse(newProduct)
    }
}