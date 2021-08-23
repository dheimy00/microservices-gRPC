package br.com.dheimy00.listener

import br.com.dheimy00.dto.PedidoDto
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@KafkaListener(groupId = "pagamento")
class PagamentoListener {

    val logger: Logger = LoggerFactory.getLogger(PagamentoListener::class.java)

    @Topic("pedidos")
    fun receboPedido(pedidoDto: PedidoDto){
        logger.info("Recebo pedido {}",pedidoDto)
        logger.info("###### PROCESSANDO PAGAMENTO ######");
        logger.info("Id do pedido: " + pedidoDto.idPedido);
        logger.info("Cliente: " + pedidoDto.clienteDto.nome);
        logger.info("Valor: R$ " + pedidoDto.precoTotal);
        logger.info("Pagamento processado com sucesso!");
        logger.info("###################################");
    }

}