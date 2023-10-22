package br.com.fiap.techchallenge.kongfood.domain.client.service

import java.util.UUID

interface ClientService {

    fun createClient(name: String, email: String, phone: String?, cpf: String?): UUID

    fun updateClient(id: UUID, name: String, email: String, phone: String?, cpf: String?)

    fun deActivateClient(id: UUID)

    fun findClientByCpf(cpf: String): UUID?
}