package br.com.dheimy00.dto

data class ResponsePedidoDto(
    val idPedido: String,
    val precoTotal: Double,
    val quantidade: Int,
    val clienteDto: ClienteDto,
    val produtoDto: ProdutoDto
)
{}