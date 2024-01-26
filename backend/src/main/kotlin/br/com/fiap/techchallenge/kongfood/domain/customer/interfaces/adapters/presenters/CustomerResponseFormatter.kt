package br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.presenters

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.customer.entities.Customer
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerResponseModel

class CustomerResponseFormatter: CustomerPresenter {
    override fun prepareSuccessResponse(customer: Customer): CustomerResponseModel {
        return CustomerResponseModel(
            id = customer.id.toString(),
            name = customer.name,
            email = customer.email,
            phone = customer.phone,
            cpf = customer.cpf?.cpf)
    }

    override fun prepareFailureResponse(error: String) {
        throw DomainException(error)
    }
}