package com.momorix.vitecmemorix

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class VitecMemorixApplication

fun main(args: Array<String>) {
	runApplication<VitecMemorixApplication>(*args)
}
