package br.com.fiap.techchallenge.kongfood.domain.customer.usecases

import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerRequestModel
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerResponseModel

fun interface CustomerUpdateBoundary {
    fun update(customerRequestModel: CustomerRequestModel, id: String): CustomerResponseModel
}