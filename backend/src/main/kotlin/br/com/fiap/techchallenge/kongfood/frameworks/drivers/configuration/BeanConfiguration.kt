package br.com.fiap.techchallenge.kongfood.frameworks.drivers.configuration

import br.com.fiap.techchallenge.kongfood.KongFoodApplication
import br.com.fiap.techchallenge.kongfood.domain.customer.entities.CustomerFactoryImpl
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.controllers.CustomerService
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.controllers.DomainCustomerService
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.presenters.CustomerResponseFormatter
import br.com.fiap.techchallenge.kongfood.domain.customer.interfaces.adapters.repository.CustomerRepository
import br.com.fiap.techchallenge.kongfood.domain.customer.usecases.*
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderFactoryImpl
import br.com.fiap.techchallenge.kongfood.domain.order.entities.OrderProductFactoryImpl
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.controllers.DomainOrderService
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.controllers.OrderService
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.presenters.OrderResponseFormatter
import br.com.fiap.techchallenge.kongfood.domain.order.interfaces.adapters.repository.OrderRepository
import br.com.fiap.techchallenge.kongfood.domain.order.usecases.*
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.controllers.DomainPaymentService
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.controllers.PaymentService
import br.com.fiap.techchallenge.kongfood.domain.payment.interfaces.adapters.presenters.PaymentResponseFormatter
import br.com.fiap.techchallenge.kongfood.domain.payment.usecases.*
import br.com.fiap.techchallenge.kongfood.domain.product.entities.ProductFactoryImpl
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.controllers.DomainProductService
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.controllers.ProductService
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.presenters.ProductResponseFormatter
import br.com.fiap.techchallenge.kongfood.domain.product.interfaces.adapters.repository.ProductRepository
import br.com.fiap.techchallenge.kongfood.domain.product.usecases.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [KongFoodApplication::class])
class BeanConfiguration {

    @Bean
    fun orderSearchBoundary(orderRepository: OrderRepository): OrderSearchBoundary {
        val orderPresenter = OrderResponseFormatter()
        return OrderSearchInteractor(orderRepository = orderRepository, orderPresenter = orderPresenter)
    }

    @Bean
    fun orderRegisterBoundary(orderRepository: OrderRepository): OrderRegisterBoundary {
        val orderPresenter = OrderResponseFormatter()
        val orderFactory = OrderFactoryImpl()
        return OrderRegisterInteractor(
            orderRepository = orderRepository,
            orderPresenter = orderPresenter,
            orderFactory = orderFactory
        )
    }

    @Bean
    fun orderAddProductBoundary(
        orderRepository: OrderRepository,
        orderSearchBoundary: OrderSearchBoundary
    ): OrderAddProductBoundary {
        val orderPresenter = OrderResponseFormatter()
        val orderProductFactory = OrderProductFactoryImpl()
        return OrderAddProductInteractor(
            orderRepository = orderRepository,
            orderPresenter = orderPresenter,
            orderSearchBoundary = orderSearchBoundary,
            orderProductFactory = orderProductFactory
        )
    }

    @Bean
    fun orderRemoveProductBoundary(
        orderRepository: OrderRepository,
        orderSearchBoundary: OrderSearchBoundary
    ): OrderRemoveProductBoundary {
        val orderPresenter = OrderResponseFormatter()
        val orderProductFactory = OrderProductFactoryImpl()
        return OrderRemoveProductInteractor(
            orderRepository = orderRepository,
            orderPresenter = orderPresenter,
            orderSearchBoundary = orderSearchBoundary,
            orderProductFactory = orderProductFactory
        )
    }

    @Bean
    fun orderChangeStateBoundary(
        orderRepository: OrderRepository,
        orderSearchBoundary: OrderSearchBoundary
    ): OrderChangeStateBoundary {
        val orderPresenter = OrderResponseFormatter()
        return OrderChangeStateInteractor(
            orderRepository = orderRepository,
            orderPresenter = orderPresenter,
            orderSearchBoundary = orderSearchBoundary
        )
    }

    @Bean
    fun orderService(
        productService: ProductService, orderSearchBoundary: OrderSearchBoundary,
        orderRegisterBoundary: OrderRegisterBoundary,
        orderAddProductBoundary: OrderAddProductBoundary,
        orderRemoveProductBoundary: OrderRemoveProductBoundary,
        orderChangeStateBoundary: OrderChangeStateBoundary
    ): OrderService {
        val orderPresenter = OrderResponseFormatter()
        return DomainOrderService(
            productService = productService,
            orderSearchBoundary = orderSearchBoundary,
            orderRegisterBoundary = orderRegisterBoundary,
            orderAddProductBoundary = orderAddProductBoundary,
            orderPresenter = orderPresenter,
            orderRemoveProductBoundary = orderRemoveProductBoundary,
            orderChangeStateBoundary = orderChangeStateBoundary
        )
    }

    @Bean
    fun customerSearchBoundary(customerRepository: CustomerRepository): CustomerSearchBoundary {
        val customerPresenter = CustomerResponseFormatter()
        return CustomerSearchInteractor(
            customerRepository = customerRepository,
            customerPresenter = customerPresenter
        )
    }

    @Bean
    fun customerChangeStatusBoundary(
        customerRepository: CustomerRepository,
        customerSearchBoundary: CustomerSearchBoundary
    ): CustomerChangeStatusBoundary {
        val customerPresenter = CustomerResponseFormatter()
        return CustomerChangeStatusInteractor(
            customerRepository = customerRepository,
            customerPresenter = customerPresenter,
            customerSearchBoundary = customerSearchBoundary
        )
    }

    @Bean
    fun customerUpdateBoundary(
        customerRepository: CustomerRepository,
        customerSearchBoundary: CustomerSearchBoundary
    ): CustomerUpdateBoundary {
        val customerFactory = CustomerFactoryImpl()
        val customerPresenter = CustomerResponseFormatter()
        return CustomerUpdateInteractor(
            customerRepository = customerRepository,
            customerFactory = customerFactory,
            customerPresenter = customerPresenter,
            customerSearchBoundary = customerSearchBoundary
        )
    }

    @Bean
    fun customerRegisterBoundary(
        customerRepository: CustomerRepository,
        customerSearchBoundary: CustomerSearchBoundary
    ): CustomerRegisterBoundary {
        val customerFactory = CustomerFactoryImpl()
        val customerPresenter = CustomerResponseFormatter()
        return CustomerRegisterInteractor(
            customerRepository = customerRepository,
            customerFactory = customerFactory,
            customerPresenter = customerPresenter,
            customerSearchBoundary = customerSearchBoundary
        )
    }

    @Bean
    fun customerService(
        customerRegisterBoundary: CustomerRegisterBoundary,
        customerUpdateBoundary: CustomerUpdateBoundary,
        customerChangeStatusBoundary: CustomerChangeStatusBoundary,
        customerSearchBoundary: CustomerSearchBoundary
    ): CustomerService {
        return DomainCustomerService(
            customerRegisterBoundary,
            customerUpdateBoundary,
            customerChangeStatusBoundary,
            customerSearchBoundary
        )
    }

    @Bean
    fun productRegisterBoundary(productRepository: ProductRepository): ProductRegisterBoundary {
        val productFactory = ProductFactoryImpl()
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
    fun productUpdateBoundary(
        productRepository: ProductRepository,
        productSearchBoundary: ProductSearchBoundary
    ): ProductUpdateBoundary {
        val productFactory = ProductFactoryImpl()
        val productPresenter = ProductResponseFormatter()
        return ProductUpdateInteractor(
            productRepository = productRepository,
            productFactory = productFactory,
            productPresenter = productPresenter,
            productSearchBoundary = productSearchBoundary
        )
    }

    @Bean
    fun productChangeStatusBoundary(
        productRepository: ProductRepository,
        productSearchBoundary: ProductSearchBoundary
    ): ProductChangeStatusBoundary {
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

    @Bean
    fun paymentNotificationBoundary(orderService: OrderService): PaymentNotificationBoundary {
        val paymentPresenter = PaymentResponseFormatter()
        return PaymentNotificationInteractor(orderService, paymentPresenter)
    }

    @Bean
    fun paymentRegisterBoundary(orderService: OrderService): PaymentRegisterBoundary {
        val paymentPresenter = PaymentResponseFormatter()
        return PaymentRegisterInteractor(orderService, paymentPresenter)
    }

    @Bean
    fun paymentVerificationBoundary(orderService: OrderService): PaymentVerificationBoundary {
        val paymentPresenter = PaymentResponseFormatter()
        return PaymentVerificationInteractor(orderService, paymentPresenter)
    }

    @Bean
    fun paymentService(
        paymentNotificationBoundary: PaymentNotificationBoundary,
        paymentRegisterBoundary: PaymentRegisterBoundary,
        paymentVerificationBoundary: PaymentVerificationBoundary
    ): PaymentService {
        return DomainPaymentService(paymentNotificationBoundary, paymentRegisterBoundary, paymentVerificationBoundary)
    }
}