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
class ProductChangeStatusInteractorUnitTest {

    private final val productRepository: ProductRepository = mock()
    private final val productPresenter = ProductResponseFormatter()
    private final val productSearchBoundary: ProductSearchBoundary = mock()
    val productChangeStatusInteractor = ProductChangeStatusInteractor (productRepository, productPresenter, productSearchBoundary)

    @Test
    fun `should change product status`() {
        val mockProduct = ProductProvider.getProduct()

        Mockito.`when`(productRepository.findById(any())).thenReturn(Optional.of(mockProduct))

        productChangeStatusInteractor.changeStatus(mockProduct.id)

        Mockito.verify(productRepository, Mockito.times(1)).findById(any())
        Mockito.verify(productRepository, Mockito.times(1)).save(any())
    }
}