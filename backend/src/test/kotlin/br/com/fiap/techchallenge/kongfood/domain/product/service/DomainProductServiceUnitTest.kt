package br.com.fiap.techchallenge.kongfood.domain.product.service

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.DomainException
import br.com.fiap.techchallenge.kongfood.domain.product.ProductProvider
import br.com.fiap.techchallenge.kongfood.domain.product.repository.ProductRepository
import br.com.fiap.techchallenge.kongfood.domain.product.service.dto.ProductDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Optional

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KongFoodApplicationTests::class])
class DomainProductServiceUnitTest {

    private final val productRepository: ProductRepository = mock()
    val domainProductService = DomainProductService(productRepository)

    @Test
    fun `should add product`() {
        Mockito.`when`(productRepository.findByName("Hamburguer")).thenReturn(null)

        val result = domainProductService.addProduct(ProductProvider.getProductDTO())

        Assertions.assertNotNull(result)
        Mockito.verify(productRepository, Mockito.times(1)).findByName("Hamburguer")
        Mockito.verify(productRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should throw exception when try to add product with same name`() {
        Mockito.`when`(productRepository.findByName("Hamburguer")).thenReturn(ProductProvider.getProduct())

        Assertions.assertThrows(DomainException::class.java) {
            domainProductService.addProduct(ProductProvider.getProductDTO())
        }
        Mockito.verify(productRepository, Mockito.times(1)).findByName("Hamburguer")
        Mockito.verify(productRepository, Mockito.times(0)).save(any())
    }

    @Test
    fun `should update a prduct`(){
        val mockProduct = ProductProvider.getProduct()
        val mockProductDTO = ProductDTO.convertFromEntityToDTO(mockProduct)
        mockProductDTO.price = "10.50"
        mockProductDTO.description = "A very delicious hamburger"

        Mockito.`when`(productRepository.findById(any())).thenReturn(Optional.of(mockProduct))
        Mockito.`when`(productRepository.findByName(any())).thenReturn(mockProduct)

        val result = domainProductService.updateProduct(mockProductDTO)

        Assertions.assertNotNull(result)
        Assertions.assertEquals(mockProductDTO.price, result.price)
        Assertions.assertEquals(mockProductDTO.description, result.description)
        Mockito.verify(productRepository, Mockito.times(1)).findById(any())
        Mockito.verify(productRepository, Mockito.times(1)).findByName(any())
        Mockito.verify(productRepository, Mockito.times(1)).save(any())
    }

    @Test
    fun `should throw exception when try to update a product that does not exist`(){
        val mockProduct = ProductProvider.getProduct()
        val mockProductDTO = ProductDTO.convertFromEntityToDTO(mockProduct)
        mockProductDTO.price = "10.50"
        mockProductDTO.description = "A very delicious hamburger"

        Mockito.`when`(productRepository.findById(any())).thenReturn(Optional.empty())

        Assertions.assertThrows(DomainException::class.java) {
            domainProductService.updateProduct(mockProductDTO)
        }
        Mockito.verify(productRepository, Mockito.times(1)).findById(any())
        Mockito.verify(productRepository, Mockito.times(0)).findByName(any())
        Mockito.verify(productRepository, Mockito.times(0)).save(any())
    }

    @Test
    fun `should throw exception when try to change product category`(){
        val mockProduct = ProductProvider.getProduct()
        val mockProductDTO = ProductDTO.convertFromEntityToDTO(mockProduct)
        mockProductDTO.category = "Drinks"

        Mockito.`when`(productRepository.findById(any())).thenReturn(Optional.of(mockProduct))
        Mockito.`when`(productRepository.findByName(any())).thenReturn(mockProduct)

        Assertions.assertThrows(DomainException::class.java) {
            domainProductService.updateProduct(mockProductDTO)
        }
        Mockito.verify(productRepository, Mockito.times(1)).findById(any())
        Mockito.verify(productRepository, Mockito.times(1)).findByName(any())
        Mockito.verify(productRepository, Mockito.times(0)).save(any())
    }

    @Test
    fun `should change product status`(){
        val mockProduct = ProductProvider.getProduct()

        Mockito.`when`(productRepository.findById(any())).thenReturn(Optional.of(mockProduct))

        domainProductService.changeStatus(mockProduct.id)

        Mockito.verify(productRepository, Mockito.times(1)).findById(any())
        Mockito.verify(productRepository, Mockito.times(1)).save(any())
    }

    //should find product by id
    @Test
    fun `should find product by id`(){
        val mockProduct = ProductProvider.getProduct()

        Mockito.`when`(productRepository.findById(any())).thenReturn(Optional.of(mockProduct))

        val result = domainProductService.findProductById(mockProduct.id)

        Assertions.assertNotNull(result)
        Mockito.verify(productRepository, Mockito.times(1)).findById(any())
    }

    //should find product by category
    @Test
    fun `should find product by category`(){
        val mockProduct = ProductProvider.getProduct()

        Mockito.`when`(productRepository.findByCategory(any())).thenReturn(listOf(mockProduct))

        val result = domainProductService.findProductByCategory(mockProduct.category.toString())

        Assertions.assertNotNull(result)
        Assertions.assertTrue(result.isNotEmpty())
        Mockito.verify(productRepository, Mockito.times(1)).findByCategory(any())
    }

    //should throw exception when try to find product by category that does not exist
    @Test
    fun `should throw exception when try to find product by category that does not exist`(){
        Assertions.assertThrows(DomainException::class.java) {
            domainProductService.findProductByCategory("Comida_goiana")
        }
    }

    //should find all products
    @Test
    fun `should find all products`(){
        Mockito.`when`(productRepository.findAll()).thenReturn(listOf(ProductProvider.getProduct()))

        val result = domainProductService.findAll()

        Assertions.assertNotNull(result)
        Assertions.assertTrue(result.isNotEmpty())
        Mockito.verify(productRepository, Mockito.times(1)).findAll()
    }
}