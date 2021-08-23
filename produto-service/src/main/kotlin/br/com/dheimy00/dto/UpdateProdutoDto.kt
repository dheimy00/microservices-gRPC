package br.com.dheimy00.dto

data class UpdateProdutoDto(
    val idProduto: String?,
    val identificadorProduto : String,
    val nome: String,
    val preco: Double,
    val descricao: String
) {}