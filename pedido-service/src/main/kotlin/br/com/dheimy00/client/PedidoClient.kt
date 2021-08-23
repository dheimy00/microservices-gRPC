package br.com.dheimy00.client

import br.com.dheimy00.dto.ResponsePedidoDto
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
interface PedidoClient {

    @Topic("pedidos")
    fun send(responsePedidoDto: ResponsePedidoDto)
}