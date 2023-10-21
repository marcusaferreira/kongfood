package br.com.fiap.techchallenge.kongfood

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KongfoodApplication

fun main(args: Array<String>) {
	runApplication<KongfoodApplication>(*args)
}
