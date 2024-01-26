package br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.models

data class CustomerResponseModel(
    val id: String,
    val name: String,
    val email: String,
    val phone: String?,
    val cpf: String?
)
