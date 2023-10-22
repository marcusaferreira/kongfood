package br.com.fiap.techchallenge.kongfood.domain.customer

import java.util.UUID

class Customer(
    val id: UUID,
    val name: String,
    val email: String,
    val phone: String?,
    val cpf: String?,
    var isActive: Boolean = true
) {}