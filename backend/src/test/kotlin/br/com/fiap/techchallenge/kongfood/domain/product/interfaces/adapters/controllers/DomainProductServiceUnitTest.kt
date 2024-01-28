package br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.controllers

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.product.ProductProvider
import br.com.fiap.techchallenge.kongfood.domain.product.usecases.ProductChangeStatusBoundary
import br.com.fiap.techchallenge.kongfood.domain.product.usecases.ProductRegisterBoundary
import br.com.fiap.techchallenge.kongfood.domain.product.usecases.ProductSearchBoundary
import br.com.fiap.techchallenge.kongfood.domain.product.usecases.ProductUpdateBoundary
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KongFoodApplicationTests::class])
class DomainProductServiceUnitTest {

    private final val productRegisterBoundary: ProductRegisterBoundary = mock()
    private final val productUpdateBoundary: ProductUpdateBoundary = mock()
    private final val productChangeStatusBoundary: ProductChangeStatusBoundary = mock()
    private final val productSearchBoundary: ProductSearchBoundary = mock()
    val domainProductService = DomainProductService(
        productRegisterBoundary,
        productUpdateBoundary,
        productChangeStatusBoundary,
        productSearchBoundary
    )

    @Test
    fun `should add product`() {
        Mockito.`when`(productRegisterBoundary.registerProduct(any())).thenReturn(ProductProvider.getProductResponse())

        val result = domainProductService.addProduct(ProductProvider.getProductDTO())

        Assertions.assertNotNull(result)
        Mockito.verify(productRegisterBoundary, Mockito.times(1)).registerProduct(any())
    }

    @Test
    fun `should update product`() {
        Mockito.`when`(productUpdateBoundary.update(any())).thenReturn(ProductProvider.getProductResponse())

        val result = domainProductService.updateProduct(ProductProvider.getProductDTO())

        Assertions.assertNotNull(result)
        Mockito.verify(productUpdateBoundary, Mockito.times(1)).update(any())
    }

    @Test
    fun `should change status product`() {
        domainProductService.changeStatus(ProductProvider.getProduct().id)

        Mockito.verify(productChangeStatusBoundary, Mockito.times(1)).changeStatus(any())
    }

    @Test
    fun `should find product by id`() {
        Mockito.`when`(productSearchBoundary.findById(any())).thenReturn(ProductProvider.getProductResponse())

        val result = domainProductService.findProductById(ProductProvider.getProduct().id)

        Assertions.assertNotNull(result)
        Mockito.verify(productSearchBoundary, Mockito.times(1)).findById(any())
    }

    @Test
    fun `should find product by category`() {
        Mockito.`when`(productSearchBoundary.findByCategory(any())).thenReturn(listOf(ProductProvider.getProductResponse()))

        val result = domainProductService.findProductByCategory("Hamburguer")

        Assertions.assertNotNull(result)
        Mockito.verify(productSearchBoundary, Mockito.times(1)).findByCategory(any())
    }

    @Test
    fun `should find all products`() {
        Mockito.`when`(productSearchBoundary.findAll()).thenReturn(listOf(ProductProvider.getProductResponse()))

        val result = domainProductService.findAll()

        Assertions.assertNotNull(result)
        Mockito.verify(productSearchBoundary, Mockito.times(1)).findAll()
    }
}