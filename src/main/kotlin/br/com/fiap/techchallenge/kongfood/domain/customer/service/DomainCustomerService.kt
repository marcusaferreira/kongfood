package br.com.fiap.techchallenge.kongfood.domain.customer.service

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.customer.repository.CustomerRepository
import br.com.fiap.techchallenge.kongfood.domain.customer.service.dto.CustomerDTO
import java.util.*

class DomainCustomerService(
    val customerRepository: CustomerRepository
) : CustomerService {
    override fun createCustomer(customerDTO: CustomerDTO): UUID {
        verifyIfCustomerAlreadyExists(customerDTO)
        val newCustomer = customerDTO.toNewCustomer()
        customerRepository.save(newCustomer)
        return newCustomer.id
    }

    override fun updateCustomer(customerDTO: CustomerDTO, id: String): CustomerDTO {
        val customer = customerRepository.findCustomerById(UUID.fromString(id))
        if (customer.isEmpty) {
            throw DomainException("Customer not found for the id $id")
        }
        if (customerDTO.id!! != customer.get().id) {
            throw DomainException("Customer id cannot be changed")
        }
        verifyIfCustomerByEmailAlreadyExists(customerDTO)
        if (customerDTO.cpf != customer.get().cpf.toString()) {
            throw DomainException("CPF cannot be changed")
        }
        if (!customer.get().isActive) {
            throw DomainException("Customer is not active")
        }

        customerRepository.save(customerDTO.toEntity())
        return customerDTO
    }

    override fun deActivateCustomer(id: String) {
        val customer = customerRepository.findCustomerById(UUID.fromString(id))
        if (customer.isEmpty) {
            throw DomainException("Customer not found for the id $id")
        }
        customer.get().isActive = false
        customerRepository.save(customer.get())
    }

    override fun findCustomerByCpf(cpf: String): CustomerDTO {
        val customer = customerRepository.findCustomerByCpf(cpf)
        if (customer.isEmpty) {
            throw DomainException("Customer not found for the CPF $cpf")
        }
        return CustomerDTO.convertFromEntityToDTO(customer.get())
    }

    override fun findCustomerById(id: String): CustomerDTO {
        val customer = customerRepository.findCustomerById(UUID.fromString(id))
        if (customer.isEmpty) {
            throw DomainException("Customer not found for the id $id")
        }
        return CustomerDTO.convertFromEntityToDTO(customer.get())
    }

    override fun findCustomerByEmail(email: String): CustomerDTO {
        val customer = customerRepository.findCustomerByEmail(email)
        if (customer.isEmpty) {
            throw DomainException("Customer not found for the e-mail $email")
        }
        return CustomerDTO.convertFromEntityToDTO(customer.get())
    }

    override fun activateCustomer(id: String) {
        val customer = customerRepository.findCustomerById(UUID.fromString(id))
        if (customer.isEmpty) {
            throw DomainException("Customer not found for the id $id")
        }
        customer.get().isActive = true
        customerRepository.save(customer.get())
    }

    private fun verifyIfCustomerAlreadyExists(customerDTO: CustomerDTO) {
        verifyIfCustomerByCpfAlreadyExists(customerDTO)
        verifyIfCustomerByEmailAlreadyExists(customerDTO)
    }

    private fun verifyIfCustomerByEmailAlreadyExists(customerDTO: CustomerDTO) {
        val customer = customerRepository.findCustomerByEmail(customerDTO.email)
        if (customer.isPresent) {
            throw DomainException("Customer with e-mail ${customerDTO.email} already exists")
        }
    }

    private fun verifyIfCustomerByCpfAlreadyExists(customerDTO: CustomerDTO) {
        val customer = customerRepository.findCustomerByCpf(customerDTO.cpf!!)
        if (customer.isPresent && (customerDTO.id != null && customerDTO.id != customer.get().id)) {
            throw DomainException("Customer with CPF ${customerDTO.cpf} already exists")
        }
    }
}