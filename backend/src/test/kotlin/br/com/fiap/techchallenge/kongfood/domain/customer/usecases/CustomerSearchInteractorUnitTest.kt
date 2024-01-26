package br.com.fiap.techchallenge.kongfood.domain.customer.usecases

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.customer.CustomerProvider
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
class CustomerSearchInteractorUnitTest {

    private val customerRepository: CustomerRepository = mock()
    private val customerPresenter = CustomerResponseFormatter()
    val customerSearchInteractor = CustomerSearchInteractor(customerRepository, customerPresenter)

    @Test
    fun `should find a customer by cpf`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerByCpf(any())).thenReturn(Optional.of(validCustomer))
        val customerDTO = customerSearchInteractor.findCustomerByCpf(validCustomer.cpf.toString())
        //then
        Assertions.assertNotNull(customerDTO)
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByCpf(any())
    }

    @Test
    fun `should throw a domain exception when try to find a customer by cpf not found`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerByCpf(any())).thenReturn(Optional.empty())
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerSearchInteractor.findCustomerByCpf(validCustomer.cpf.toString())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByCpf(any())
    }

    @Test
    fun `should find a customer by id`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.of(validCustomer))
        val customerDTO = customerSearchInteractor.findCustomerById(validCustomer.id.toString())
        //then
        Assertions.assertNotNull(customerDTO)
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
    }

    @Test
    fun `should throw a domain exception when try to find a customer by id not found`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.empty())
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerSearchInteractor.findCustomerById(validCustomer.id.toString())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
    }

    @Test
    fun `should find a customer by email`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerByEmail(any())).thenReturn(Optional.of(validCustomer))
        val customerDTO = customerSearchInteractor.findCustomerByEmail(validCustomer.email)
        //then
        Assertions.assertNotNull(customerDTO)
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByEmail(any())
    }

    @Test
    fun `should throw a domain exception when try to find a customer by email not found`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerByEmail(any())).thenReturn(Optional.empty())
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerSearchInteractor.findCustomerByEmail(validCustomer.email)
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByEmail(any())
    }

    @Test
    fun `should verify if customer exists`() {
        val validCustomer = CustomerProvider().getValidCustomer()
        customerSearchInteractor.exisits(Optional.of(validCustomer), validCustomer.id)
    }

    @Test
    fun `should throw a domain exception when try to verify if customer exists and not found`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        Assertions.assertThrows(DomainException::class.java) {
            customerSearchInteractor.exisits(Optional.empty(), validCustomer.id)
        }
    }

    @Test
    fun `should verify if customer by email already exists`() {
        //when
        Mockito.`when`(customerRepository.verifyIfCustomerByEmailAlreadyExists(any())).thenReturn(true)
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerSearchInteractor.verifyIfCustomerByEmailAlreadyExists(CustomerProvider().getCustomerToBeCreated())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).verifyIfCustomerByEmailAlreadyExists(any())
    }

    @Test
    fun `should verify if customer by email already exists and not found`() {
        //when
        Mockito.`when`(customerRepository.verifyIfCustomerByEmailAlreadyExists(any())).thenReturn(false)
        customerSearchInteractor.verifyIfCustomerByEmailAlreadyExists(CustomerProvider().getCustomerToBeCreated())
        //then
        Mockito.verify(customerRepository, Mockito.times(1)).verifyIfCustomerByEmailAlreadyExists(any())
    }
}