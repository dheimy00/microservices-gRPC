package br.com.dheimy00.service.implementation

import br.com.dheimy00.ClienteResponse
import br.com.dheimy00.ListaClienteResponse
import br.com.dheimy00.dto.ClienteDto
import br.com.dheimy00.dto.ResponseClienteDto
import br.com.dheimy00.dto.UpdateClienteDto
import br.com.dheimy00.exception.ClienteNotFoundException
import br.com.dheimy00.model.Cliente
import br.com.dheimy00.repository.ClienteRepository
import br.com.dheimy00.service.ClienteService
import jakarta.inject.Singleton
import java.util.*


@Singleton
class ClienteServiceImpl(private val clienteRepository: ClienteRepository) : ClienteService {

    override fun findAll(): ListaClienteResponse {
        val clientes = ListaClienteResponse.newBuilder()
        for (cliente in clienteRepository.findAll()) {
            clientes.addClientes(
                ClienteResponse.newBuilder().setIdCliente(cliente.idCliente)
                    .setNome(cliente.nome)
                    .setCpf(cliente.cpf).build()
            )
        }
        return clientes.build()
    }

    override fun save(clienteDto: ClienteDto): ResponseClienteDto {

        val idCliente = UUID.randomUUID().toString()
        val cliente = clienteRepository.save(Cliente(null, idCliente, clienteDto.nome, clienteDto.cpf))
        return ResponseClienteDto(cliente.idCliente, cliente.nome, cliente.cpf)

    }

    override fun get(idCliente: String): ResponseClienteDto {

        val cliente = clienteRepository.findByIdCliente(idCliente).orElseThrow {
            throw ClienteNotFoundException("Cliente não encontrada com id ${idCliente} informado")
        }
        return ResponseClienteDto(cliente.idCliente, cliente.nome, cliente.cpf)

    }

    override fun update(updateClienteDto: UpdateClienteDto?): ResponseClienteDto {

        val cliente = clienteRepository.findByIdCliente(updateClienteDto?.idCliente!!).orElseThrow {
            throw ClienteNotFoundException("Cliente não encontrada com id ${updateClienteDto.idCliente} informado")
        }
        cliente.nome = updateClienteDto.nome
        cliente.cpf = updateClienteDto.cpf

        return ResponseClienteDto(cliente.idCliente, cliente.nome, cliente.cpf)

    }

    override fun delete(idCliente: String) {

        val cliente = clienteRepository.findByIdCliente(idCliente).orElseThrow {
            throw ClienteNotFoundException("Cliente não encontrada com id ${idCliente} informado")
        }
        clienteRepository.delete(cliente)

    }
}