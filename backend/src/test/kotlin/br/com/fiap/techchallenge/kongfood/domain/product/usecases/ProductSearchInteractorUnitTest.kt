package br.com.fiap.techchallenge.kongfood.domain.product.usecases

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.product.ProductProvider
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.models.ProductRequestModel
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.presenters.ProductResponseFormatter
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.repository.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*


@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KongFoodApplicationTests::class])
class ProductSearchInteractorUnitTest {

    private final val productRepository: ProductRepository = mock()
    private final val productPresenter = ProductResponseFormatter()
    val productSearchInteractor = ProductSearchInteractor (productRepository, productPresenter)

    //should find product by id
    @Test
    fun `should find product by id`() {
        val mockProduct = ProductProvider.getProduct()

        Mockito.`when`(productRepository.findById(any())).thenReturn(Optional.of(mockProduct))

        val result = productSearchInteractor.findById(mockProduct.id)

        Assertions.assertNotNull(result)
        Mockito.verify(productRepository, Mockito.times(1)).findById(any())
    }

    //should throw exception when try to find product by id that does not exist
    @Test
    fun `should throw exception when try to find product by id that does not exist`() {
        Assertions.assertThrows(DomainException::class.java) {
            productSearchInteractor.findById(UUID.randomUUID())
        }
    }

    //should find product by category
    @Test
    fun `should find product by category`() {
        val mockProduct = ProductProvider.getProduct()

        Mockito.`when`(productRepository.findByCategory(any())).thenReturn(listOf(mockProduct))

        val result = productSearchInteractor.findByCategory(mockProduct.category.toString())

        Assertions.assertNotNull(result)
        Assertions.assertTrue(result.isNotEmpty())
        Mockito.verify(productRepository, Mockito.times(1)).findByCategory(any())
    }

    //should throw exception when try to find product by category that does not exist
    @Test
    fun `should throw exception when try to find product by category that does not exist`() {
        Assertions.assertThrows(DomainException::class.java) {
            productSearchInteractor.findByCategory("Comida_goiana")
        }
    }

    //should find all products
    @Test
    fun `should find all products`() {
        Mockito.`when`(productRepository.findAll()).thenReturn(listOf(ProductProvider.getProduct()))

        val result = productSearchInteractor.findAll()

        Assertions.assertNotNull(result)
        Assertions.assertTrue(result.isNotEmpty())
        Mockito.verify(productRepository, Mockito.times(1)).findAll()
    }
}