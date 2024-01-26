package br.com.fiap.techchallenge.kongfood.frameworks.drivers.db

import br.com.fiap.techchallenge.kongfood.frameworks.drivers.db.repository.CustomerRepositorySpringDataMongo
import br.com.fiap.techchallenge.kongfood.frameworks.drivers.db.repository.OrderRepositorySpringDataMongo
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@EnableMongoRepositories(basePackageClasses = [OrderRepositorySpringDataMongo::class,
    CustomerRepositorySpringDataMongo::class])
class MongoDBConfiguration {
}