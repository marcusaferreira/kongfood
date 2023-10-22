package br.com.fiap.techchallenge.kongfood.infrastructure.configuration

import br.com.fiap.techchallenge.kongfood.infrastructure.repository.CustomerRepositorySpringDataMongo
import br.com.fiap.techchallenge.kongfood.infrastructure.repository.OrderRepositorySpringDataMongo
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@EnableMongoRepositories(basePackageClasses = [OrderRepositorySpringDataMongo::class,
    CustomerRepositorySpringDataMongo::class])
class MongoDBConfiguration {
}