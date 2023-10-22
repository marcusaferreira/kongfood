package br.com.fiap.techchallenge.kongfood.infrastracture.configuration

import br.com.fiap.techchallenge.kongfood.infrastracture.repository.CustomerRepositorySpringDataMongo
import br.com.fiap.techchallenge.kongfood.infrastracture.repository.OrderRepositorySpringDataMongo
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@EnableMongoRepositories(basePackageClasses = [OrderRepositorySpringDataMongo::class,
    CustomerRepositorySpringDataMongo::class])
class MongoDBConfiguration {
}