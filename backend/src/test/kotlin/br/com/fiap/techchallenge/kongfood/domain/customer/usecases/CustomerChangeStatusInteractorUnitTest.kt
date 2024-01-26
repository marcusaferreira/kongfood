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
class CustomerChangeStatusInteractorUnitTest {

    private final val customerRepository: CustomerRepository = mock()
    private final val customerSearchBoundary: CustomerSearchBoundary = mock()
    private final val customerPresenter = CustomerResponseFormatter()
    val customerChangeStatusInteractor =
        CustomerChangeStatusInteractor(customerRepository, customerPresenter, customerSearchBoundary)

    @Test
    fun `should deactivate a customer`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.of(validCustomer))
        customerChangeStatusInteractor.deactivate(validCustomer.id)
        //then
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should activate a customer`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.of(validCustomer))
        customerChangeStatusInteractor.activate(validCustomer.id)
        //then
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(1)).save(any())
    }

}