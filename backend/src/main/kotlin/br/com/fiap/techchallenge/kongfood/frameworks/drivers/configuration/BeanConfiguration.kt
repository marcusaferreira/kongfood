package br.com.fiap.techchallenge.kongfood.frameworks.drivers.configuration

import br.com.fiap.techchallenge.kongfood.KongFoodApplication
import br.com.fiap.techchallenge.kongfood.domain.customer.repository.CustomerRepository
import br.com.fiap.techchallenge.kongfood.domain.customer.service.CustomerService
import br.com.fiap.techchallenge.kongfood.domain.customer.service.DomainCustomerService
import br.com.fiap.techchallenge.kongfood.domain.order.repository.OrderRepository
import br.com.fiap.techchallenge.kongfood.domain.order.service.DomainOrderService
import br.com.fiap.techchallenge.kongfood.domain.order.service.OrderService
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductFactoryImplementation
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.repository.ProductRepository
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.controller.DomainProductService
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.controller.ProductService
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.presenters.ProductResponseFormatter
import br.com.fiap.techchallenge.kongfood.domain.product.usecases.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [KongFoodApplication::class])
class BeanConfiguration {

    @Bean
    @Autowired
    fun orderService(orderRepository: OrderRepository, productService: ProductService): OrderService {
        return DomainOrderService(orderRepository = orderRepository, productService = productService)
    }

    @Bean
    fun customerService(customerRepository: CustomerRepository): CustomerService {
        return DomainCustomerService(customerRepository = customerRepository)
    }

    @Bean
    fun productRegisterBoundary(productRepository: ProductRepository): ProductRegisterBoundary {
        val productFactory = ProductFactoryImplementation()
        val productPresenter = ProductResponseFormatter()
        return ProductRegisterInteractor(
            productRepository = productRepository,
            productFactory = productFactory,
            productPresenter = productPresenter
        )
    }

    @Bean
    fun productSearchBoundary(productRepository: ProductRepository): ProductSearchBoundary {
        val productPresenter = ProductResponseFormatter()
        return ProductSearchInteractor(
            productRepository = productRepository,
            productPresenter = productPresenter
        )
    }

    @Bean
    fun productUpdateBoundary(productRepository: ProductRepository, productSearchBoundary: ProductSearchBoundary): ProductUpdateBoundary {
        val productFactory = ProductFactoryImplementation()
        val productPresenter = ProductResponseFormatter()
        return ProductUpdateInteractor(
            productRepository = productRepository,
            productFactory = productFactory,
            productPresenter = productPresenter,
            productSearchBoundary = productSearchBoundary
        )
    }

    @Bean
    fun productChangeStatusBoundary(productRepository: ProductRepository, productSearchBoundary: ProductSearchBoundary): ProductChangeStatusBoundary {
        val productPresenter = ProductResponseFormatter()
        return ProductChangeStatusInteractor(
            productRepository = productRepository,
            productPresenter = productPresenter,
            productSearchBoundary = productSearchBoundary
        )
    }

    @Bean
    fun productService(
        productRepository: ProductRepository,
        productRegisterBoundary: ProductRegisterBoundary,
        productUpdateBoundary: ProductUpdateBoundary,
        productChangeStatusBoundary: ProductChangeStatusBoundary,
        productSearchBoundary: ProductSearchBoundary
    ): ProductService {
        return DomainProductService(
            productRegisterBoundary = productRegisterBoundary,
            productUpdateBoundary = productUpdateBoundary,
            productChangeStatusBoundary = productChangeStatusBoundary,
            productSearchBoundary = productSearchBoundary
        )
    }
}