package br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models

import br.com.fiap.techchallenge.kongfood.domain.product.entities.Product

data class ProductResponseModel(
    var id: String?,
    var price: String,
    var name: String,
    var description: String,
    var category: String,
    var status: Boolean? = true,
) {
    companion object {
        fun convertFromEntity(product: Product): ProductResponseModel {
            return ProductResponseModel(
                product.id.toString(),
                product.price.toString(),
                product.name,
                product.description,
                product.category.type,
                product.status
            )
        }
    }

}
