package br.com.dheimy00.service

import br.com.dheimy00.ListaProdutoResponse
import br.com.dheimy00.dto.ProdutoDto
import br.com.dheimy00.dto.ProdutoDtoResponse
import br.com.dheimy00.dto.UpdateProdutoDto

interface ProdutoService {

    fun findAll(): ListaProdutoResponse
    fun save(produtoDto: ProdutoDto): ProdutoDtoResponse
    fun get(idProduto: String): ProdutoDtoResponse
    fun update(updateProdutoDto: UpdateProdutoDto): ProdutoDtoResponse
    fun delete(idProduto: String)
}