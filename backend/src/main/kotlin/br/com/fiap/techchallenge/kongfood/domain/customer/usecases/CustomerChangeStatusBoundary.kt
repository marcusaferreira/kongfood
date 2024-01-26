package br.com.fiap.techchallenge.kongfood.domain.customer.usecases

import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerResponseModel
import java.util.*

interface CustomerChangeStatusBoundary {
    fun activate(customerID: UUID) : CustomerResponseModel

    fun deactivate(customerID: UUID) : CustomerResponseModel
}
