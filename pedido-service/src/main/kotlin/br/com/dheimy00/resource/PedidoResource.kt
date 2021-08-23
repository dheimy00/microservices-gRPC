package br.com.dheimy00.resource

import br.com.dheimy00.*
import br.com.dheimy00.dto.PedidoDto
import br.com.dheimy00.exception.PedidoNotFoundException
import br.com.dheimy00.service.PedidoService
import io.grpc.Status
import io.grpc.stub.StreamObserver
import jakarta.inject.Singleton

@Singleton
class PedidoResource(private val pedidoService: PedidoService) : PedidoServiceServiceGrpc.PedidoServiceServiceImplBase() {

    override fun findAll(request: Empty?, responseObserver: StreamObserver<ListaPedidoResponse>?) {

        try {
            responseObserver?.onNext(pedidoService.findAll())
            responseObserver?.onCompleted()

        } catch (e: PedidoNotFoundException) {
            responseObserver?.onError(Status.NOT_FOUND.withDescription(e.message).asRuntimeException())
        }
    }

    override fun save(createPedidoRequest: CreatePedidoRequest?, responseObserver: StreamObserver<PedidoResponse>?) {

        val pedidoDto = PedidoDto(
            createPedidoRequest?.quantidade!!,
            createPedidoRequest?.cliente.idCliente,
            createPedidoRequest?.produto.idProduto
        )
        val pedidoDtoResponse = pedidoService.save(pedidoDto)

        val response = PedidoResponse.newBuilder()
            .setIdPedido(pedidoDtoResponse.idPedido)
            .setQuantidade(pedidoDtoResponse.quantidade)
            .setPrecoTotal(pedidoDtoResponse.precoTotal)
            .setIdCliente(pedidoDtoResponse.clienteDto.idCliente)
            .setIdProduto(pedidoDtoResponse.produtoDto.idProduto)
            .build()

        responseObserver?.onNext(response)
        responseObserver?.onCompleted()

    }

    override fun get(pedidoRequestId: PedidoRequestId?, responseObserver: StreamObserver<PedidoResponse>?) {
        try {

            val pedidoDtoResponse = pedidoService.get(pedidoRequestId?.idPedido!!)

            val response = PedidoResponse.newBuilder()
                .setIdPedido(pedidoDtoResponse.idPedido)
                .setQuantidade(pedidoDtoResponse.quantidade)
                .setPrecoTotal(pedidoDtoResponse.precoTotal)
                .setIdCliente(pedidoDtoResponse.clienteDto.idCliente)
                .setIdProduto(pedidoDtoResponse.produtoDto.idProduto)
                .build()

            responseObserver?.onNext(response)
            responseObserver?.onCompleted()

        } catch (e: PedidoNotFoundException) {
            responseObserver?.onError(Status.NOT_FOUND.withDescription(e.message).asRuntimeException())
        }
    }

}