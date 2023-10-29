package br.com.fiap.techchallenge.kongfood.domain.product.service

import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.product.Product
import br.com.fiap.techchallenge.kongfood.domain.product.ProductCategory
import br.com.fiap.techchallenge.kongfood.domain.product.repository.ProductRepository
import br.com.fiap.techchallenge.kongfood.domain.product.service.dto.ProductDTO
import java.util.*

class DomainProductService(
    val productRepository: ProductRepository
) : ProductService {
    override fun addProduct(product: ProductDTO): UUID {
        val newProduct = product.toEntity()
        validateProductDoesNotExist(newProduct)
        productRepository.save(newProduct)
        return newProduct.id
    }

    override fun updateProduct(product: ProductDTO): ProductDTO {
        val productToUpdate = product.toEntity()
        val savedProduct = productRepository.findById(product.id!!)

        validateProductExist(savedProduct, product.id)
        validateProductDoesNotExist(productToUpdate)
        validateProductIsSameCategory(savedProduct, productToUpdate)
        productRepository.save(productToUpdate)

        return product
    }

    override fun changeStatus(productID: UUID) {
        val product = productRepository.findById(productID)
        validateProductExist(product, productID)
        val productChanged = Product(product.get(), !product.get().status)
        productRepository.save(productChanged)
    }

    override fun findProductById(productID: UUID): ProductDTO {
        val product = productRepository.findById(productID)
        validateProductExist(product, productID)
        return ProductDTO.convertFromEntityToDTO(product.get())
    }

    override fun findProductByCategory(category: String): List<ProductDTO> {
        val productCategory = ProductCategory.getEnumByString(category) ?: throw DomainException("Category not found")
        val products = productRepository.findByCategory(productCategory)

        return products.filter { product -> product.status }.map { ProductDTO.convertFromEntityToDTO(it) }
    }

    private fun validateProductExist(product: Optional<Product>, productID: UUID) {
        if (product.isEmpty) {
            throw DomainException("Product not founded for the ID $productID")
        }
    }

    private fun validateProductDoesNotExist(product: Product) {
        val productExist = productRepository.findByName(product.name)
        if (productExist != null) {
            throw DomainException("Product already exists")
        }
    }

    private fun validateProductIsSameCategory(
        savedProduct: Optional<Product>,
        productToUpdate: Product
    ) {
        if (savedProduct.get().category != productToUpdate.category) {
            throw DomainException("Product category can't be changed")
        }
    }
}