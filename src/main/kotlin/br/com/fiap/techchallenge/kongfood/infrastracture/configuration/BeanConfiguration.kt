package br.com.fiap.techchallenge.kongfood.infrastracture.configuration

import br.com.fiap.techchallenge.kongfood.KongfoodApplication
import br.com.fiap.techchallenge.kongfood.domain.order.repository.OrderRepository
import br.com.fiap.techchallenge.kongfood.domain.order.service.DomainOrderService
import br.com.fiap.techchallenge.kongfood.domain.order.service.OrderService
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
}