package br.com.fiap.techchallenge.kongfood.domain.customer.service.dto

import br.com.fiap.techchallenge.kongfood.domain.customer.CPF
import br.com.fiap.techchallenge.kongfood.domain.customer.Customer
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.util.*

data class CustomerDTO(
    val id: UUID?,
    @field:NotBlank(message = "Name cannot be blank")
    val name: String,
    @field:NotBlank(message = "Name cannot be blank")
    @field:Email(message = "Email must be valid")
    val email: String,
    val phone: String?,
    @field:Pattern(regexp = "^[0-9]{11}$", message = "CPF must be only numbers and 11 digits")
    val cpf: String?
) {
    fun toNewCustomer(): Customer {
        return Customer(UUID.randomUUID(), name, email, phone, if (cpf != null) CPF(cpf) else null)
    }

    fun toEntity(): Customer {
        return Customer(id!!, name, email, phone, if (cpf != null) CPF(cpf) else null)
    }

    companion object {
        fun convertFromEntityToDTO(customer: Customer): CustomerDTO {
            return CustomerDTO(customer.id, customer.name, customer.email, customer.phone, customer.cpf.toString())
        }
    }

}
