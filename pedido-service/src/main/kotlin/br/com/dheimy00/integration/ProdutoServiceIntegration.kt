package br.com.dheimy00.integration

import br.com.dheimy00.ProdutoRequestId
import br.com.dheimy00.configuration.ChannelFactory
import br.com.dheimy00.dto.ProdutoDto
import br.com.dheimy00.exception.PedidoNotFoundException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ProdutoServiceIntegration (val channelFactory: ChannelFactory){

    val logger: Logger = LoggerFactory.getLogger(ProdutoServiceIntegration::class.java)

    fun getProduto(idProduto: String): ProdutoDto {

        logger.info(" BUSCANDO DADOS DO PRODUTO NO PRODUTO DE SERVIÇO")

        val produtoResponse = channelFactory.produtoSub().get(
            ProdutoRequestId.newBuilder().setIdProduto(idProduto).build()
        )

        if(produtoResponse == null){
            throw PedidoNotFoundException("Produto não encontrada id ${produtoResponse?.idProduto} informada")
        }

        logger.info("Dados encontrados!");
        logger.info("#################################################");
        return ProdutoDto(produtoResponse.idProduto,produtoResponse.identificadorProduto,produtoResponse.nome,produtoResponse.preco,produtoResponse.descricao)
    }
}