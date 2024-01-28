package br.com.fiap.techchallenge.kongfood.domain.order.entities

import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.models.OrderLineRequestModel
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductCategory

class OrderProductFactoryImpl : OrderProductFactory {
    override fun createOrderProduct(orderLine: OrderLineRequestModel): Product {
        return Product(orderLine.productId, orderLine.price, orderLine.name, orderLine.description,
            ProductCategory.getEnumByString(orderLine.category)!!)
    }
}