package br.com.fiap.techchallenge.kongfood.domain.client

import java.util.UUID

class Client(
    val id: UUID,
    val name: String,
    val email: String,
    val phone: String?,
    val cpf: String?,
    var isActive: Boolean = true
) {}