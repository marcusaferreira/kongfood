package br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.controllers

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.customer.CustomerProvider
import br.com.fiap.techchallenge.kongfood.domain.customer.usecases.CustomerChangeStatusBoundary
import br.com.fiap.techchallenge.kongfood.domain.customer.usecases.CustomerRegisterBoundary
import br.com.fiap.techchallenge.kongfood.domain.customer.usecases.CustomerSearchBoundary
import br.com.fiap.techchallenge.kongfood.domain.customer.usecases.CustomerUpdateBoundary
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.UUID

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KongFoodApplicationTests::class])
class DomainCustomerServiceUnitTest {

    private val customerRegisterBoundary: CustomerRegisterBoundary = mock()
    private val customerUpdateBoundary: CustomerUpdateBoundary = mock()
    private val customerChangeStatusBoundary: CustomerChangeStatusBoundary = mock()
    private val customerSearchBoundary: CustomerSearchBoundary = mock()
    val customerService = DomainCustomerService(
        customerRegisterBoundary,
        customerUpdateBoundary,
        customerChangeStatusBoundary,
        customerSearchBoundary
    )

    @Test
    fun `should create a customer`() {
        //given
        val customerDTO = CustomerProvider().getCustomerToBeCreated()
        //when
        Mockito.`when`(customerRegisterBoundary.register(any())).thenReturn(CustomerProvider().getValidCustomerResponse())
        val customer = customerService.createCustomer(customerDTO)
        //then
        Assertions.assertNotNull(customer)
        Mockito.verify(customerRegisterBoundary, Mockito.times(1)).register(any())
    }

    @Test
    fun `should update a customer`() {
        //given
        val customerDTO = CustomerProvider().getCustomerToBeCreated()
        //when
        Mockito.`when`(customerUpdateBoundary.update(any(), any())).thenReturn(CustomerProvider().getValidCustomerResponse())
        val customer = customerService.updateCustomer(customerDTO, UUID.randomUUID().toString())
        //then
        Assertions.assertNotNull(customer)
        Mockito.verify(customerUpdateBoundary, Mockito.times(1)).update(any(), any())
    }

    @Test
    fun `should deactivate a customer`() {
        //when
        Mockito.`when`(customerChangeStatusBoundary.deactivate(any())).thenReturn(CustomerProvider().getValidCustomerResponse())
        val customer = customerService.deActivateCustomer(UUID.randomUUID().toString())
        //then
        Assertions.assertNotNull(customer)
        Mockito.verify(customerChangeStatusBoundary, Mockito.times(1)).deactivate(any())
    }

    @Test
    fun `should find a customer by cpf`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerSearchBoundary.findCustomerByCpf(any())).thenReturn(CustomerProvider().getValidCustomerResponse())
        val customerDTO = customerService.findCustomerByCpf(validCustomer.cpf.toString())
        //then
        Assertions.assertNotNull(customerDTO)
        Mockito.verify(customerSearchBoundary, Mockito.times(1)).findCustomerByCpf(any())
    }

    @Test
    fun `should find a customer by id`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerSearchBoundary.findCustomerById(any())).thenReturn(CustomerProvider().getValidCustomerResponse())
        val customerDTO = customerService.findCustomerById(validCustomer.id.toString())
        //then
        Assertions.assertNotNull(customerDTO)
        Mockito.verify(customerSearchBoundary, Mockito.times(1)).findCustomerById(any())
    }

    @Test
    fun `should find a customer by email`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerSearchBoundary.findCustomerByEmail(any())).thenReturn(CustomerProvider().getValidCustomerResponse())
        val customerDTO = customerService.findCustomerByEmail(validCustomer.email)
        //then
        Assertions.assertNotNull(customerDTO)
        Mockito.verify(customerSearchBoundary, Mockito.times(1)).findCustomerByEmail(any())
    }

    @Test
    fun `should activate a customer`() {
        //when
        Mockito.`when`(customerChangeStatusBoundary.activate(any())).thenReturn(CustomerProvider().getValidCustomerResponse())
        val customer = customerService.activateCustomer(UUID.randomUUID().toString())
        //then
        Assertions.assertNotNull(customer)
        Mockito.verify(customerChangeStatusBoundary, Mockito.times(1)).activate(any())
    }


}

