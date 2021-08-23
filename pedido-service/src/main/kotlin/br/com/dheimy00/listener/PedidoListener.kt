package br.com.dheimy00.listener

import io.micronaut.configuration.kafka.annotation.KafkaListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@KafkaListener(groupId = "pagamento")
class PedidoListener {

    val logger: Logger = LoggerFactory.getLogger(PedidoListener::class.java)

}