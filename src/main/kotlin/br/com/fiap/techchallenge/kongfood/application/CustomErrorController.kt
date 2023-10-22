package br.com.fiap.techchallenge.kongfood.application

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.GetMapping

const val PATH = "/error"
class CustomErrorController: ErrorController {

    @GetMapping(PATH)
    fun error(): String {
        return "Error haven"
    }
}