package br.com.fiap.techchallenge.kongfood.domain.customer.usecases

import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerResponseModel
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.presenters.CustomerPresenter
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.repository.CustomerRepository
import java.util.*

class CustomerChangeStatusInteractor(
    val customerRepository: CustomerRepository,
    val customerPresenter: CustomerPresenter,
    val customerSearchBoundary: CustomerSearchBoundary
): CustomerChangeStatusBoundary {
    override fun activate(customerID: UUID): CustomerResponseModel {
        val customer = customerRepository.findCustomerById(customerID)
        customerSearchBoundary.exisits(customer, customerID)
        customer.get().isActive = true
        customerRepository.save(customer.get())
        return customerPresenter.prepareSuccessResponse(customer.get())
    }

    override fun deactivate(customerID: UUID): CustomerResponseModel {
        val customer = customerRepository.findCustomerById(customerID)
        customerSearchBoundary.exisits(customer, customerID)
        customer.get().isActive = false
        customerRepository.save(customer.get())
        return customerPresenter.prepareSuccessResponse(customer.get())
    }

}