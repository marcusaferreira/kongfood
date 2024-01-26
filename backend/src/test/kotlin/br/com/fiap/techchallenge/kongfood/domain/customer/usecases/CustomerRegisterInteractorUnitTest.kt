package br.com.fiap.techchallenge.kongfood.domain.customer.usecases

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.customer.CustomerProvider
import br.com.fiap.techchallenge.kongfood.domain.customer.entities.CustomerFactoryImplementation
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
class CustomerRegisterInteractorUnitTest {

    private val customerRepository: CustomerRepository = mock()
    private val customerFactory = CustomerFactoryImplementation()
    private val customerPresenter = CustomerResponseFormatter()
    private val customerSearchBoundary : CustomerSearchBoundary = mock()
    val customerRegisterInteractor = CustomerRegisterInteractor(
        customerRepository,
        customerFactory,
        customerPresenter,
        customerSearchBoundary)

    @Test
    fun `should create a customer`() {
        //given
        val customerDTO = CustomerProvider().getCustomerToBeCreated()
        //when
        Mockito.`when`(customerRepository.findCustomerByCpf(customerDTO.cpf!!)).thenReturn(Optional.empty())
        Mockito.doNothing().`when`(customerSearchBoundary).verifyIfCustomerByEmailAlreadyExists(customerDTO)
        val customer = customerRegisterInteractor.register(customerDTO)
        //then
        Assertions.assertNotNull(customer)
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByCpf(customerDTO.cpf!!)
        Mockito.verify(customerSearchBoundary, Mockito.times(1)).verifyIfCustomerByEmailAlreadyExists(customerDTO)
        Mockito.verify(customerRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should throw exception when customer already exists by cpf`() {
        //given
        val customerDTO = CustomerProvider().getCustomerToBeCreated()
        //when
        Mockito.`when`(customerRepository.findCustomerByCpf(customerDTO.cpf!!)).thenReturn(Optional.of(CustomerProvider().getValidCustomer()))
        Mockito.`when`(customerRepository.findCustomerByEmail(customerDTO.email)).thenReturn(Optional.empty())
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerRegisterInteractor.register(customerDTO)
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByCpf(customerDTO.cpf!!)
        Mockito.verify(customerRepository, Mockito.times(0)).findCustomerByEmail(customerDTO.email)
        Mockito.verify(customerRepository, Mockito.times(0)).save(any())
    }
}