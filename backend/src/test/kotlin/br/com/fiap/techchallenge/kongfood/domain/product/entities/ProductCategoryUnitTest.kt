package br.com.fiap.techchallenge.kongfood.domain.product.entities

import br.com.fiap.techchallenge.kongfood.KongFoodApplicationTests
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductCategory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [KongFoodApplicationTests::class])
class ProductCategoryUnitTest {

    @Test
    fun `should return product category by string`() {
        val result = ProductCategory.getEnumByString("Drinks")
        assert(result == ProductCategory.DRINKS)
    }

    @Test
    fun `should return null when product category not found`() {
        val result = ProductCategory.getEnumByString("Not found")
        assert(result == null)
    }

    @Test
    fun `should return product category by string with special characters`() {
        val result = ProductCategory.getEnumByString("Main courses!")
        assert(result == ProductCategory.MAIN_COURSES)
    }

    @Test
    fun `should return product category by string with underscore`() {
        val result = ProductCategory.getEnumByString("Side_dishes")
        assert(result == ProductCategory.SIDE_DISHES)
    }

    @Test
    fun `should return product category by string with dash character`() {
        val result = ProductCategory.getEnumByString("Side-dishes")
        assert(result == ProductCategory.SIDE_DISHES)
    }
}