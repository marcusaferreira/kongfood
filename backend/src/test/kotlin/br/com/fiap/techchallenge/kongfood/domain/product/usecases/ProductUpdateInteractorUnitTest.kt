package br.com.fiap.techchallenge.kongfood.domain.product.usecases

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.product.ProductProvider
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductFactory
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductFactoryImpl
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
class ProductUpdateInteractorUnitTest {

    private final val productRepository: ProductRepository = mock()
    private final val productFactory: ProductFactory = ProductFactoryImpl()
    private final val productPresenter = ProductResponseFormatter()
    private final val productSearchBoundary: ProductSearchBoundary = mock()
    val productUpdateInteractor = ProductUpdateInteractor(productRepository, productFactory, productPresenter, productSearchBoundary)

    @Test
    fun `should update a product`(){
        val mockProduct = ProductProvider.getProduct()
        val mockProductRequestModel = ProductRequestModel.convertFromEntity(mockProduct)
        mockProductRequestModel.price = "10.50"
        mockProductRequestModel.description = "A very delicious hamburger"

        Mockito.`when`(productRepository.findById(any())).thenReturn(Optional.of(mockProduct))
        Mockito.`when`(productRepository.exists(any())).thenReturn(false)

        val result = productUpdateInteractor.update(mockProductRequestModel)

        Assertions.assertNotNull(result)
        Assertions.assertEquals(mockProductRequestModel.price, result.price)
        Assertions.assertEquals(mockProductRequestModel.description, result.description)
        Mockito.verify(productRepository, Mockito.times(1)).findById(any())
        Mockito.verify(productRepository, Mockito.times(1)).exists(any())
        Mockito.verify(productRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should throw exception when try to update a product name but already exists another`(){
        val mockProduct = ProductProvider.getProduct()
        val mockProductRequestModel = ProductRequestModel.convertFromEntity(mockProduct)
        mockProductRequestModel.price = "10.50"
        mockProductRequestModel.description = "A very delicious hamburger"

        Mockito.`when`(productRepository.findById(any())).thenReturn(Optional.of(mockProduct))
        Mockito.`when`(productRepository.exists(any())).thenReturn(true)

        Assertions.assertThrows(DomainException::class.java) {
            productUpdateInteractor.update(mockProductRequestModel)
        }
        Mockito.verify(productRepository, Mockito.times(1)).findById(any())
        Mockito.verify(productRepository, Mockito.times(0)).findByName(any())
        Mockito.verify(productRepository, Mockito.times(0)).save(any())
    }

    @Test
    fun `should throw exception when try to change product category`(){
        val mockProduct = ProductProvider.getProduct()
        val mockProductRequestModel = ProductRequestModel.convertFromEntity(mockProduct)
        mockProductRequestModel.category = "Drinks"

        Mockito.`when`(productRepository.findById(any())).thenReturn(Optional.of(mockProduct))
        Mockito.`when`(productRepository.exists(any())).thenReturn(false)

        Assertions.assertThrows(DomainException::class.java) {
            productUpdateInteractor.update(mockProductRequestModel)
        }
        Mockito.verify(productRepository, Mockito.times(1)).findById(any())
        Mockito.verify(productRepository, Mockito.times(1)).exists(any())
        Mockito.verify(productRepository, Mockito.times(0)).save(any())
    }
}