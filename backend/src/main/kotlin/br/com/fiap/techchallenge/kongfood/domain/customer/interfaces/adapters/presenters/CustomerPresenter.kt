package br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.presenters

import br.com.fiap.techchallenge.kongfood.domain.customer.entities.Customer
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerResponseModel

interface CustomerPresenter {

    fun prepareSuccessResponse(customer: Customer): CustomerResponseModel

    fun prepareFailureResponse(error: String)

}