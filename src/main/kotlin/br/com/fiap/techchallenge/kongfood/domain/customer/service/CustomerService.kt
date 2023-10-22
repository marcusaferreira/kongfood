package br.com.fiap.techchallenge.kongfood.domain.customer.service

import java.util.UUID

interface CustomerService {

    fun createClient(name: String, email: String, phone: String?, cpf: String?): UUID

    fun updateClient(id: UUID, name: String, email: String, phone: String?, cpf: String?)

    fun deActivateClient(id: UUID)

    fun findClientByCpf(cpf: String): UUID?
}