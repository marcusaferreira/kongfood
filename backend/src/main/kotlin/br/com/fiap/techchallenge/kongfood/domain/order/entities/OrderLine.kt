package br.com.fiap.techchallenge.kongfood.domain.order.entities

class OrderLine(
    val product: Product,
    val quantity: Int,
    val note: String? = null
) {

}