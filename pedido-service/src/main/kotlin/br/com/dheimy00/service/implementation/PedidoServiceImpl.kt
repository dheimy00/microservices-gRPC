package br.com.dheimy00.service.implementation

import br.com.dheimy00.ListaPedidoResponse
import br.com.dheimy00.PedidoResponse
import br.com.dheimy00.client.PedidoClient
import br.com.dheimy00.dto.PedidoDto
import br.com.dheimy00.dto.ResponsePedidoDto
import br.com.dheimy00.exception.PedidoNotFoundException
import br.com.dheimy00.integration.ClienteServiceIntegration
import br.com.dheimy00.integration.ProdutoServiceIntegration
import br.com.dheimy00.model.Pedido
import br.com.dheimy00.repository.PedidoRepository
import br.com.dheimy00.service.PedidoService
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.*

@Singleton
class PedidoServiceImpl(
    val pedidoRepository: PedidoRepository,
    val clienteServiceIntegration: ClienteServiceIntegration,
    val produtoServiceIntegration: ProdutoServiceIntegration,
    val pedidoClient: PedidoClient
) : PedidoService {

    override fun findAll(): ListaPedidoResponse {

        val pedidos = ListaPedidoResponse.newBuilder()
        for (pedido in pedidoRepository.findAll()) {
            pedidos.addPedidos(
                PedidoResponse.newBuilder()
                    .setIdPedido(pedido.idPedido)
                    .setQuantidade(pedido.quantidade)
                    .setPrecoTotal(pedido.precoTotal)
                    .setIdCliente(pedido.idCliente)
                    .setIdProduto(pedido.idProduto)
            )
        }
        return pedidos.build()
    }

    override fun save(pedidoDto: PedidoDto): ResponsePedidoDto {

        val clienteDto = clienteServiceIntegration.getCliente(pedidoDto.idCliente)
        val produtoDto = produtoServiceIntegration.getProduto(pedidoDto.idProduto)

        val idPedido = UUID.randomUUID().toString()
        val precoTotal = produtoDto.preco * pedidoDto.quantidade
        val pedido = pedidoRepository.save( Pedido(null, idPedido, precoTotal ,pedidoDto.quantidade, clienteDto.idCliente, produtoDto.idProduto))

        var responsePedido = ResponsePedidoDto(pedido.idPedido, pedido.precoTotal, pedido.quantidade,clienteDto, produtoDto)

        //fila kafka mensagem para pagamento no parametros
        pedidoClient.send(responsePedido)
        return responsePedido

    }

    override fun get(idPedido: String): ResponsePedidoDto {

        val pedido = pedidoRepository.findByIdPedido(idPedido).orElseThrow {
            throw PedidoNotFoundException("Pedido não encontrado id ${idPedido} informado")
        }
        return ResponsePedidoDto(pedido.idPedido, pedido.precoTotal,pedido.quantidade, null!!, null!!)
    }
}