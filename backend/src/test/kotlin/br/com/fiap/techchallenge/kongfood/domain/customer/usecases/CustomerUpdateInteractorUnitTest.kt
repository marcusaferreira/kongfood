package br.com.fiap.techchallenge.kongfood.domain.customer.usecases

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.customer.CustomerProvider
import br.com.fiap.techchallenge.kongfood.domain.customer.entities.CustomerFactoryImpl
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.presenters.CustomerResponseFormatter
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.repository.CustomerRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KongFoodApplicationTests::class])
class CustomerUpdateInteractorUnitTest {

    private val customerRepository: CustomerRepository = mock()
    private val customerFactory = CustomerFactoryImpl()
    private val customerPresenter = CustomerResponseFormatter()
    private val customerSearchBoundary: CustomerSearchBoundary = mock()
    val customerUpdateInteractor =
        CustomerUpdateInteractor(customerRepository, customerFactory, customerPresenter, customerSearchBoundary)

    @Test
    fun `should update a customer`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        val customerDTO = CustomerProvider().getCreatedCustomer(validCustomer.id, "48583291004")
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.of(validCustomer))
        Mockito.doNothing().`when`(customerSearchBoundary).verifyIfCustomerByEmailAlreadyExists(customerDTO)
        val customerId = customerUpdateInteractor.update(customerDTO, customerDTO.id!!.toString())
        //then
        Assertions.assertNotNull(customerId)
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should throw a domain exception when try to update a customer with different id`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        val customerDTO = CustomerProvider().getCreatedCustomer(UUID.randomUUID(), "48583291004")
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.of(validCustomer))
        Mockito.`when`(customerRepository.findCustomerByEmail(customerDTO.email)).thenReturn(Optional.empty())
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerUpdateInteractor.update(customerDTO, customerDTO.id!!.toString())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(0)).findCustomerByEmail(customerDTO.email)
        Mockito.verify(customerRepository, Mockito.times(0)).save(any())
    }

    @Test
    fun `should throw a domain exception when try to update a customer with different cpf`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        val customerDTO = CustomerProvider().getCreatedCustomer(validCustomer.id, "12345678901")

        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.of(validCustomer))
        Mockito.doNothing().`when`(customerSearchBoundary).verifyIfCustomerByEmailAlreadyExists(customerDTO)
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerUpdateInteractor.update(customerDTO, customerDTO.id!!.toString())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(0)).save(any())
    }

    //should throw a domain exception when try to update a customer that is not active
    @Test
    fun `should throw a domain exception when try to update a customer that is not active`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        validCustomer.isActive = false
        val customerDTO = CustomerProvider().getCreatedCustomer(validCustomer.id, "48583291004")
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.of(validCustomer))
        Mockito.doNothing().`when`(customerSearchBoundary).verifyIfCustomerByEmailAlreadyExists(customerDTO)
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerUpdateInteractor.update(customerDTO, customerDTO.id!!.toString())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(0)).save(any())
    }
}