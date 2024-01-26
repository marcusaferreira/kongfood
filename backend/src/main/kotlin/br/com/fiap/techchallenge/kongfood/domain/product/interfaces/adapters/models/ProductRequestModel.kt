package br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models

import br.com.fiap.techchallenge.kongfood.domain.product.entities.Product
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ProductRequestModel(
    var id: String?,
    @field:NotBlank(message = "Price cannot be blank")
    @field:Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?\$", message = "Price must be only numbers and 2 decimal places")
    var price: String,
    @field:NotBlank(message = "Name cannot be blank")
    var name: String,
    @field:NotBlank(message = "Description cannot be blank")
    var description: String,
    @field:NotBlank(message = "Category cannot be blank")
    var category: String,
    var status: Boolean? = true,
) {

    companion object {
        fun convertFromEntity(product: Product): ProductRequestModel {
            return ProductRequestModel(
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
