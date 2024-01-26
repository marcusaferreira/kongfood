package br.com.fiap.techchallenge.kongfood.domain.product.usecases

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.product.ProductProvider
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductFactory
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductFactoryImplementation
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.repository.ProductRepository
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.presenters.ProductResponseFormatter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KongFoodApplicationTests::class])
class ProductRegisterInteractorUnitTest {

    private final val productRepository: ProductRepository = mock()
    private final val productFactory: ProductFactory = ProductFactoryImplementation()
    private final val productPresenter = ProductResponseFormatter()
    val productRegisterInterecator = ProductRegisterInteractor(productRepository, productFactory, productPresenter)

    @Test
    fun `should add product`() {
        `when`(productRepository.existsByName("Hamburguer")).thenReturn(false)

        val result = productRegisterInterecator.registerProduct(ProductProvider.getProductDTO())

        Assertions.assertNotNull(result)
        verify(productRepository, times(1)).existsByName("Hamburguer")
        verify(productRepository, times(1)).save(any())
    }

    @Test
    fun `should throw exception when try to add product with same name`() {
        `when`(productRepository.existsByName("Hamburguer")).thenReturn(true)

        Assertions.assertThrows(DomainException::class.java) {
            productRegisterInterecator.registerProduct(ProductProvider.getProductDTO())
        }
        verify(productRepository, times(1)).existsByName("Hamburguer")
        verify(productRepository, times(0)).save(any())
    }
}