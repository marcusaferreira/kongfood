package br.com.fiap.techchallenge.kongfood.domain.product.usecases

import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductResponseModel
import java.util.*

fun interface ProductChangeStatusBoundary {
    fun changeStatus(productID: UUID) : ProductResponseModel
}
