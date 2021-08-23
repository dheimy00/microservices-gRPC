package br.com.dheimy00.service

import br.com.dheimy00.ListaPedidoResponse
import br.com.dheimy00.dto.PedidoDto
import br.com.dheimy00.dto.ResponsePedidoDto

interface PedidoService {

    fun findAll() : ListaPedidoResponse
    fun save(pedidoDto: PedidoDto): ResponsePedidoDto
    fun get(idPedido: String): ResponsePedidoDto

}