package br.com.dheimy00.integration

import br.com.dheimy00.ClienteRequestId
import br.com.dheimy00.configuration.ChannelFactory
import br.com.dheimy00.dto.ClienteDto
import br.com.dheimy00.exception.PedidoNotFoundException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ClienteServiceIntegration(val channelFactory: ChannelFactory) {

    val logger: Logger = LoggerFactory.getLogger(ClienteServiceIntegration::class.java)

    fun getCliente(idCliente: String): ClienteDto {

        logger.info(" BUSCANDO DADOS DO CLIENTE NO CLIENTE DE SERVIÇO")

        val clienteResponse = channelFactory.clienteSub().get(
            ClienteRequestId.newBuilder().setIdCliente(idCliente).build()
        )

        if(clienteResponse == null){
            throw PedidoNotFoundException("Produto não encontrada id ${clienteResponse?.idCliente} informada")
        }

        logger.info("Dados encontrados!");
        logger.info("#################################################");
        return ClienteDto(clienteResponse?.idCliente!!, clienteResponse.nome, clienteResponse.cpf)
    }

}