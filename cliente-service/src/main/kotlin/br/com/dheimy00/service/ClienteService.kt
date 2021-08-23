package br.com.dheimy00.service

import br.com.dheimy00.ListaClienteResponse
import br.com.dheimy00.dto.ClienteDto
import br.com.dheimy00.dto.ResponseClienteDto
import br.com.dheimy00.dto.UpdateClienteDto

interface ClienteService {

    fun findAll(): ListaClienteResponse
    fun save(clienteDto: ClienteDto): ResponseClienteDto
    fun get(idCliente: String): ResponseClienteDto
    fun update(updateClienteDto: UpdateClienteDto?) : ResponseClienteDto
    fun delete(idCliente: String)
}