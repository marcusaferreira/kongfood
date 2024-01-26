package br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.util.*

data class CustomerRequestModel(
    val id: UUID?,
    @field:NotBlank(message = "Name cannot be blank")
    val name: String,
    @field:NotBlank(message = "Name cannot be blank")
    @field:Email(message = "Email must be valid")
    val email: String,
    val phone: String?,
    @field:Pattern(regexp = "^[0-9]{11}$", message = "CPF must be only numbers and 11 digits")
    val cpf: String?
)
