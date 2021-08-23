package br.com.dheimy00.client

import io.micronaut.configuration.kafka.annotation.KafkaClient

@KafkaClient("pagamento")
interface PagamentoClient {

    fun send()

}