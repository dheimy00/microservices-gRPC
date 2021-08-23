package br.com.dheimy00.resources

import br.com.dheimy00.*
import br.com.dheimy00.dto.ClienteDto
import br.com.dheimy00.dto.UpdateClienteDto
import br.com.dheimy00.exception.ClienteAlreadyExistException
import br.com.dheimy00.exception.ClienteNotFoundException
import br.com.dheimy00.service.ClienteService
import io.grpc.Status
import io.grpc.stub.StreamObserver
import jakarta.inject.Singleton


@Singleton
class ClienteResource (val clienteService: ClienteService) : ClienteServiceServiceGrpc.ClienteServiceServiceImplBase() {

    override fun findAll(request: Empty?, responseObserver: StreamObserver<ListaClienteResponse>?) {

        try {
            responseObserver?.onNext(clienteService.findAll())
            responseObserver?.onCompleted()

        } catch (e: ClienteNotFoundException) {
            responseObserver?.onError(Status.NOT_FOUND.withDescription(e.message).asRuntimeException())
        }
    }

    override fun save(createClienteRequest: CreateClienteRequest?, responseObserver: StreamObserver<ClienteResponse>?) {
        try {
            var clienteDto = ClienteDto(createClienteRequest?.nome!! ,createClienteRequest?.cpf!!)
            var clienteDtoResponse = clienteService.save(clienteDto)

            var response = ClienteResponse.newBuilder()
                .setIdCliente(clienteDtoResponse?.idCliente!!)
                .setNome(clienteDtoResponse.nome)
                .setCpf(clienteDtoResponse.cpf)
                .build()

            responseObserver?.onNext(response)
            responseObserver?.onCompleted()

        } catch (e: ClienteAlreadyExistException) {
            responseObserver?.onError(Status.ALREADY_EXISTS.withDescription(e.message).asRuntimeException())
        }
    }

    override fun get (clienteRequestId: ClienteRequestId, responseObserver: StreamObserver<ClienteResponse>?) {
        try {

            var clienteDtoResponse = clienteService.get(clienteRequestId?.idCliente!!)

            var response = ClienteResponse.newBuilder()
                .setIdCliente(clienteDtoResponse?.idCliente!!)
                .setNome(clienteDtoResponse.nome)
                .setCpf(clienteDtoResponse.cpf)
                .build()

            responseObserver?.onNext(response)
            responseObserver?.onCompleted()

        } catch (e: ClienteNotFoundException) {
            responseObserver?.onError(Status.NOT_FOUND.withDescription(e.message).asRuntimeException())
        }
    }

    override fun update(
        updateClienteRequest: UpdateClienteRequest?,
        responseObserver: StreamObserver<ClienteResponse>?
    ) {
        try {

            var clienteDtoResponse = clienteService.update(
                UpdateClienteDto(
                    updateClienteRequest?.idCliente!!,
                    updateClienteRequest.nome,
                    updateClienteRequest.cpf
                )
            )

            var response = ClienteResponse.newBuilder()
                .setIdCliente(clienteDtoResponse?.idCliente!!)
                .setNome(clienteDtoResponse.nome)
                .setCpf(clienteDtoResponse.cpf)
                .build()

            responseObserver?.onNext(response)
            responseObserver?.onCompleted()

        } catch (e: ClienteNotFoundException) {
            responseObserver?.onError(Status.NOT_FOUND.withDescription(e.message).asRuntimeException())
        }
    }

    override fun delete(clienteRequestId: ClienteRequestId?, responseObserver: StreamObserver<Empty>?) {
        try {
            clienteService.delete(clienteRequestId?.idCliente!!)
            responseObserver?.onNext(Empty.newBuilder().build())
            responseObserver?.onCompleted()

        } catch (e: ClienteNotFoundException) {
            responseObserver?.onError(Status.NOT_FOUND.withDescription(e.message).asRuntimeException())
        }
    }
}