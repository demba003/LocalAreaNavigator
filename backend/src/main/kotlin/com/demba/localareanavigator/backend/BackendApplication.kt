package com.demba.localareanavigator.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.stereotype.Component

@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}

@Component
class CustomContainer : WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    override fun customize(factory: ConfigurableServletWebServerFactory) {
        factory.setContextPath("")
        if(System.getenv("PORT")!=null) {
            factory.setPort(Integer.valueOf(System.getenv("PORT")))

        }
    }
}