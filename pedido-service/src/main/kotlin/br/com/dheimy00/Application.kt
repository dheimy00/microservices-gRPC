package br.com.dheimy00

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("br.com.dheimy00")
		.start()
}

