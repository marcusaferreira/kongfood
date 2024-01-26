package br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.presenters

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.product.entities.Product
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductResponseModel

class ProductResponseFormatter : ProductPresenter {
    override fun prepareSuccessResponse(product: Product): ProductResponseModel {
        return ProductResponseModel.convertFromEntity(product)
    }

    override fun prepareFailureResponse(error: String) {
        throw DomainException(error)
    }

    override fun prepareAllSuccessResponse(products: List<Product>): List<ProductResponseModel> {
        val responses = mutableListOf<ProductResponseModel>()
        products.forEach { product ->
            responses.add(ProductResponseModel.convertFromEntity(product)) }

        return responses
    }
}