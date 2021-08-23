package br.com.dheimy00.dto

data class PedidoDto(
    val idPedido: String,
    val precoTotal: Double,
    val clienteDto: ClienteDto,
    val produtoDto: ProdutoDto

){}