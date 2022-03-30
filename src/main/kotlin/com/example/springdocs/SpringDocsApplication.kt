package com.example.springdocs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringDocsApplication

fun main(args: Array<String>) {
	runApplication<SpringDocsApplication>(*args)
}
