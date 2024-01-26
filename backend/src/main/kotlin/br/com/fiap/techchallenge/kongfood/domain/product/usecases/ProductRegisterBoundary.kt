package br.com.fiap.techchallenge.kongfood.domain.product.usecases

import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductRequestModel
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductResponseModel
import java.util.UUID

fun interface ProductRegisterBoundary {

    fun registerProduct(productRequestModel: ProductRequestModel): ProductResponseModel
}