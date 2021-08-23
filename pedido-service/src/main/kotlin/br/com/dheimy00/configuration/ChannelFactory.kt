package br.com.dheimy00.configuration

import br.com.dheimy00.ClienteServiceServiceGrpc
import br.com.dheimy00.ProdutoServiceServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Factory
class ChannelFactory(
    @Inject
    @GrpcChannel("cliente")
    val clienteChannel: ManagedChannel,

    @Inject
    @GrpcChannel("produto")
    val produtoChannel: ManagedChannel
) {

    @Singleton
    fun clienteSub(): ClienteServiceServiceGrpc.ClienteServiceServiceBlockingStub {
        return ClienteServiceServiceGrpc.newBlockingStub(clienteChannel)
    }

    @Singleton
    fun produtoSub(): ProdutoServiceServiceGrpc.ProdutoServiceServiceBlockingStub{
        return  ProdutoServiceServiceGrpc.newBlockingStub(produtoChannel)
    }

}