package br.com.fiap.techchallenge.kongfood.domain.client.repository

import br.com.fiap.techchallenge.kongfood.domain.client.Client
import java.util.*

interface ClientRepository {

    fun save(client: Client)

    fun findClientByCpf(cpf: String): Optional<Client>

    fun findClientById(id: UUID): Optional<Client>
}