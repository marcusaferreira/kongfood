package br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.presenters

import br.com.fiap.techchallenge.kongfood.domain.product.entities.Product
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductResponseModel

interface ProductPresenter {

    fun prepareSuccessResponse(product: Product): ProductResponseModel

    fun prepareFailureResponse(error: String)
    fun prepareAllSuccessResponse(products: List<Product>): List<ProductResponseModel>
}