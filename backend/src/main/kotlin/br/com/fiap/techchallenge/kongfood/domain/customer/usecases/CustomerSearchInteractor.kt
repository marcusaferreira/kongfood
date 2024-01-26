package br.com.fiap.techchallenge.kongfood.domain.customer.usecases

import br.com.fiap.techchallenge.kongfood.domain.customer.entities.Customer
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerRequestModel
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models.CustomerResponseModel
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.presenters.CustomerPresenter
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.repository.CustomerRepository
import java.util.*

class CustomerSearchInteractor(
    val customerRepository: CustomerRepository,
    val customerPresenter: CustomerPresenter
): CustomerSearchBoundary {
    override fun findCustomerByCpf(cpf: String): CustomerResponseModel {
        val customer = customerRepository.findCustomerByCpf(cpf)
        if (customer.isEmpty) {
            customerPresenter.prepareFailureResponse("Customer not found for the CPF $cpf")
        }
        return customerPresenter.prepareSuccessResponse(customer.get())
    }

    override fun findCustomerById(id: String): CustomerResponseModel {
        val customer = customerRepository.findCustomerById(UUID.fromString(id))
        exisits(customer, UUID.fromString(id))
        return customerPresenter.prepareSuccessResponse(customer.get())
    }

    override fun findCustomerByEmail(email: String): CustomerResponseModel {
        val customer = customerRepository.findCustomerByEmail(email)
        if (customer.isEmpty) {
            customerPresenter.prepareFailureResponse("Customer not found for the e-mail $email")
        }
        return customerPresenter.prepareSuccessResponse(customer.get())
    }

    override fun exisits(customer: Optional<Customer>, customerID: UUID) {
        if (customer.isEmpty) {
            customerPresenter.prepareFailureResponse("Customer not found for the id $customerID")
        }
    }

    override fun verifyIfCustomerByEmailAlreadyExists(customerRequestModel: CustomerRequestModel) {
        if (customerRepository.verifyIfCustomerByEmailAlreadyExists(customerRequestModel)) {
            customerPresenter.prepareFailureResponse("Customer with e-mail ${customerRequestModel.email} already exists")
        }
    }

}