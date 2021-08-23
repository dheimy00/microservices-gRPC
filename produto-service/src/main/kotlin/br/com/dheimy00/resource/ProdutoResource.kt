package br.com.dheimy00.resource

import br.com.dheimy00.*
import br.com.dheimy00.dto.ProdutoDto
import br.com.dheimy00.dto.UpdateProdutoDto
import br.com.dheimy00.exception.ProdutoAlreadyExistException
import br.com.dheimy00.exception.ProdutoNotFoundException
import br.com.dheimy00.service.ProdutoService
import io.grpc.Status
import io.grpc.stub.StreamObserver
import jakarta.inject.Singleton

@Singleton
class ProdutoResource(private val produtoService: ProdutoService) :
    ProdutoServiceServiceGrpc.ProdutoServiceServiceImplBase() {

    override fun findAll(request: Empty?, responseObserver: StreamObserver<ListaProdutoResponse>?) {

        try {
            responseObserver?.onNext(produtoService.findAll())
            responseObserver?.onCompleted()

        } catch (e: ProdutoNotFoundException) {
            responseObserver?.onError(Status.NOT_FOUND.withDescription(e.message).asRuntimeException())
        }
    }

    override fun save(createProdutoRequest: CreateProdutoRequest?, responseObserver: StreamObserver<ProdutoResponse>?) {
        try {
            var produtoDto = ProdutoDto(createProdutoRequest?.identificadorProduto!!,createProdutoRequest?.nome!!,createProdutoRequest.preco, createProdutoRequest.descricao)
            var produtoDtoResponse = produtoService.save(produtoDto)

            var response = ProdutoResponse.newBuilder()
                .setIdProduto(produtoDtoResponse?.idProduto!!)
                .setIdentificadorProduto(produtoDtoResponse.identificadorProduto)
                .setNome(produtoDtoResponse.nome)
                .setPreco(produtoDtoResponse.preco)
                .setDescricao(produtoDtoResponse.descricao)
                .build()

            responseObserver?.onNext(response)
            responseObserver?.onCompleted()

        } catch (e: ProdutoAlreadyExistException) {
            responseObserver?.onError(Status.ALREADY_EXISTS.withDescription(e.message).asRuntimeException())
        }
    }

    override fun get (produtoRequestId: ProdutoRequestId?, responseObserver: StreamObserver<ProdutoResponse>?) {
        try {

            var produtoDtoResponse = produtoService.get(produtoRequestId?.idProduto!!)

            var response = ProdutoResponse.newBuilder()
                .setIdProduto(produtoDtoResponse?.idProduto!!)
                .setIdentificadorProduto(produtoDtoResponse.identificadorProduto)
                .setNome(produtoDtoResponse.nome)
                .setPreco(produtoDtoResponse.preco)
                .setDescricao(produtoDtoResponse.descricao)
                .build()

            responseObserver?.onNext(response)
            responseObserver?.onCompleted()

        } catch (e: ProdutoNotFoundException) {
            responseObserver?.onError(Status.NOT_FOUND.withDescription(e.message).asRuntimeException())
        }
    }

    override fun update(
        updateProdutoRequest: UpdateProdutoRequest?,
        responseObserver: StreamObserver<ProdutoResponse>?
    ) {
        try {

            var produtoDtoResponse = produtoService.update(
                UpdateProdutoDto(
                    updateProdutoRequest?.idProduto!!,
                    updateProdutoRequest.identificadorProduto,
                    updateProdutoRequest.nome,
                    updateProdutoRequest.preco,
                    updateProdutoRequest.descricao

                )
            )

            var response = ProdutoResponse.newBuilder()
                .setIdProduto(produtoDtoResponse?.idProduto!!)
                .setIdentificadorProduto(produtoDtoResponse.identificadorProduto)
                .setNome(produtoDtoResponse.nome)
                .setPreco(produtoDtoResponse.preco)
                .setDescricao(produtoDtoResponse.descricao)
                .build()

            responseObserver?.onNext(response)
            responseObserver?.onCompleted()

        } catch (e: ProdutoNotFoundException) {
            responseObserver?.onError(Status.NOT_FOUND.withDescription(e.message).asRuntimeException())
        }
    }

    override fun delete(produtoRequestId: ProdutoRequestId?, responseObserver: StreamObserver<Empty>?) {
        try {
            produtoService.delete(produtoRequestId?.idProduto!!)
            responseObserver?.onNext(Empty.newBuilder().build())
            responseObserver?.onCompleted()

        } catch (e: ProdutoNotFoundException) {
            responseObserver?.onError(Status.NOT_FOUND.withDescription(e.message).asRuntimeException())
        }
    }

}