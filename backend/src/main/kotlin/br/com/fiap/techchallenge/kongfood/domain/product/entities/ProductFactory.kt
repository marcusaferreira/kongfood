package br.com.fiap.techchallenge.kongfood.domain.product.entities

import java.util.*

fun interface ProductFactory {

    fun create(productID: UUID?, name: String, description: String, price: String, category: String): Product
}