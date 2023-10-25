package br.com.fiap.techchallenge.kongfood.infrastructure.configuration

import br.com.fiap.techchallenge.kongfood.KongfoodApplication
import br.com.fiap.techchallenge.kongfood.domain.customer.repository.CustomerRepository
import br.com.fiap.techchallenge.kongfood.domain.customer.service.CustomerService
import br.com.fiap.techchallenge.kongfood.domain.customer.service.DomainCustomerService
import br.com.fiap.techchallenge.kongfood.domain.order.repository.OrderRepository
import br.com.fiap.techchallenge.kongfood.domain.order.service.DomainOrderService
import br.com.fiap.techchallenge.kongfood.domain.order.service.OrderService
import br.com.fiap.techchallenge.kongfood.domain.product.repository.ProductRepository
import br.com.fiap.techchallenge.kongfood.domain.product.service.DomainProductService
import br.com.fiap.techchallenge.kongfood.domain.product.service.ProductService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [KongfoodApplication::class])
class BeanConfiguration {

    @Bean
    fun orderService(orderRepository: OrderRepository): OrderService {
        return DomainOrderService(orderRepository = orderRepository)
    }

    @Bean
    fun customerService(customerRepository: CustomerRepository): CustomerService {
        return DomainCustomerService(customerRepository = customerRepository)
    }

    @Bean
    fun productService(productRepository: ProductRepository): ProductService {
        return DomainProductService(productRepository = productRepository)
    }
}