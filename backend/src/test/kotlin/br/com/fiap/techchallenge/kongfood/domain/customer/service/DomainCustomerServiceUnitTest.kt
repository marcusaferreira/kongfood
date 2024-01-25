package br.com.fiap.techchallenge.kongfood.domain.customer.service

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.customer.repository.CustomerRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Optional
import java.util.UUID

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KongFoodApplicationTests::class])
class DomainCustomerServiceUnitTest {

    private val customerRepository: CustomerRepository = mock()
    val customerService = DomainCustomerService(customerRepository)

    @Test
    fun `should create a customer`() {
        //given
        val customerDTO = CustomerProvider().getCustomerToBeCreated()
        //when
        Mockito.`when`(customerRepository.findCustomerByCpf(customerDTO.cpf!!)).thenReturn(Optional.empty())
        Mockito.`when`(customerRepository.findCustomerByEmail(customerDTO.email)).thenReturn(Optional.empty())
        val customerId = customerService.createCustomer(customerDTO)
        //then
        Assertions.assertNotNull(customerId)
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByCpf(customerDTO.cpf!!)
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByEmail(customerDTO.email)
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
            customerService.createCustomer(customerDTO)
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByCpf(customerDTO.cpf!!)
        Mockito.verify(customerRepository, Mockito.times(0)).findCustomerByEmail(customerDTO.email)
        Mockito.verify(customerRepository, Mockito.times(0)).save(any())
    }

    @Test
    fun `should throw exception when customer already exists by email`() {
        //given
        val customerDTO = CustomerProvider().getCustomerToBeCreated()
        //when
        Mockito.`when`(customerRepository.findCustomerByCpf(customerDTO.cpf!!)).thenReturn(Optional.empty())
        Mockito.`when`(customerRepository.findCustomerByEmail(customerDTO.email)).thenReturn(Optional.of(CustomerProvider().getValidCustomer()))
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerService.createCustomer(customerDTO)
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByCpf(customerDTO.cpf!!)
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByEmail(customerDTO.email)
        Mockito.verify(customerRepository, Mockito.times(0)).save(any())
    }

    @Test
    fun `should update a customer`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        val customerDTO = CustomerProvider().getCreatedCustomer(validCustomer.id, "48583291004")
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.of(validCustomer))
        Mockito.`when`(customerRepository.findCustomerByEmail(customerDTO.email)).thenReturn(Optional.empty())
        val customerId = customerService.updateCustomer(customerDTO, customerDTO.id!!.toString())
        //then
        Assertions.assertNotNull(customerId)
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByEmail(customerDTO.email)
        Mockito.verify(customerRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should throw a domain exception when try to update a customer not found by id`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        val customerDTO = CustomerProvider().getCreatedCustomer(validCustomer.id, "48583291004")
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.empty())
        Mockito.`when`(customerRepository.findCustomerByEmail(customerDTO.email)).thenReturn(Optional.empty())
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerService.updateCustomer(customerDTO, customerDTO.id!!.toString())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(0)).findCustomerByEmail(customerDTO.email)
        Mockito.verify(customerRepository, Mockito.times(0)).save(any())
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
            customerService.updateCustomer(customerDTO, customerDTO.id!!.toString())
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
        Mockito.`when`(customerRepository.findCustomerByEmail(customerDTO.email)).thenReturn(Optional.empty())
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerService.updateCustomer(customerDTO, customerDTO.id!!.toString())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByEmail(customerDTO.email)
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
        Mockito.`when`(customerRepository.findCustomerByEmail(customerDTO.email)).thenReturn(Optional.empty())
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerService.updateCustomer(customerDTO, customerDTO.id!!.toString())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByEmail(customerDTO.email)
        Mockito.verify(customerRepository, Mockito.times(0)).save(any())
    }

    @Test
    fun `should deactivate a customer`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.of(validCustomer))
        customerService.deActivateCustomer(validCustomer.id.toString())
        //then
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should throw a domain exception when try to deactivate a customer not found by id`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.empty())
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerService.deActivateCustomer(validCustomer.id.toString())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(0)).save(any())
    }

    @Test
    fun `should find a customer by cpf`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerByCpf(any())).thenReturn(Optional.of(validCustomer))
        val customerDTO = customerService.findCustomerByCpf(validCustomer.cpf.toString())
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
            customerService.findCustomerByCpf(validCustomer.cpf.toString())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByCpf(any())
    }

    @Test
    fun `should find a customer by id`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.of(validCustomer))
        val customerDTO = customerService.findCustomerById(validCustomer.id.toString())
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
            customerService.findCustomerById(validCustomer.id.toString())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
    }

    @Test
    fun `should find a customer by email`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerByEmail(any())).thenReturn(Optional.of(validCustomer))
        val customerDTO = customerService.findCustomerByEmail(validCustomer.email)
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
            customerService.findCustomerByEmail(validCustomer.email)
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerByEmail(any())
    }

    @Test
    fun `should activate a customer`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.of(validCustomer))
        customerService.activateCustomer(validCustomer.id.toString())
        //then
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should throw a domain exception when try to activate a customer not found by id`() {
        //given
        val validCustomer = CustomerProvider().getValidCustomer()
        //when
        Mockito.`when`(customerRepository.findCustomerById(any())).thenReturn(Optional.empty())
        //then
        Assertions.assertThrows(DomainException::class.java) {
            customerService.activateCustomer(validCustomer.id.toString())
        }
        Mockito.verify(customerRepository, Mockito.times(1)).findCustomerById(any())
        Mockito.verify(customerRepository, Mockito.times(0)).save(any())
    }
}

