package br.com.dheimy00.service.implementation

import br.com.dheimy00.ListaProdutoResponse
import br.com.dheimy00.ProdutoResponse
import br.com.dheimy00.dto.ProdutoDto
import br.com.dheimy00.dto.ProdutoDtoResponse
import br.com.dheimy00.dto.UpdateProdutoDto
import br.com.dheimy00.exception.ProdutoAlreadyExistException
import br.com.dheimy00.exception.ProdutoNotFoundException
import br.com.dheimy00.model.Produto
import br.com.dheimy00.respository.ProdutoRepository
import br.com.dheimy00.service.ProdutoService
import jakarta.inject.Singleton
import java.util.*

@Singleton
class ProdutoServiceImpl(private val produtoRepository: ProdutoRepository) : ProdutoService {

    override fun findAll(): ListaProdutoResponse {

        val produtos = ListaProdutoResponse.newBuilder()
        for (produto in produtoRepository.findAll()) {
            produtos.addProdutos(
                ProdutoResponse.newBuilder()
                    .setIdProduto(produto?.idProduto!!)
                    .setIdentificadorProduto(produto.identificadorProduto)
                    .setNome(produto.nome)
                    .setPreco(produto.preco)
                    .setDescricao(produto.descricao)
                    .build()
            )
        }
        return produtos.build()
    }

    override fun save(produtoDto: ProdutoDto): ProdutoDtoResponse {

        var produtoNome = produtoRepository.findByNome(produtoDto.nome)
        if (produtoNome.isPresent) {
            throw ProdutoAlreadyExistException("Produto já existe nome ${produtoDto.nome}")
        }

        var idProduto = UUID.randomUUID().toString()
        var produto = produtoRepository.save(
            Produto(
                null,
                idProduto,
                produtoDto.identificadorProduto,
                produtoDto.nome,
                produtoDto.preco,
                produtoDto.descricao
            )
        )
        return ProdutoDtoResponse(produto.idProduto,produto.identificadorProduto, produto.nome, produto.preco, produto.descricao)
    }

    override fun get(idProduto: String): ProdutoDtoResponse {
        var produto = produtoRepository.findByIdProduto(idProduto).orElseThrow {
            throw ProdutoNotFoundException("Produto não encontrada com id ${idProduto} informado")
        }
        return ProdutoDtoResponse(produto.idProduto,produto.identificadorProduto, produto.nome, produto.preco, produto.descricao)
    }

    override fun update(updateProdutoDto: UpdateProdutoDto): ProdutoDtoResponse {

        var produto = produtoRepository.findByIdProduto(updateProdutoDto.idProduto!!).orElseThrow {
            throw ProdutoNotFoundException("Produto não encontrada com id ${updateProdutoDto?.idProduto} informado")
        }

        produto.nome = updateProdutoDto?.nome!!
        produto.identificadorProduto = updateProdutoDto.identificadorProduto
        produto.preco = updateProdutoDto.preco
        produto.descricao = updateProdutoDto.descricao
        produto = produtoRepository.save(produto)
        return ProdutoDtoResponse(produto.idProduto,produto.identificadorProduto, produto.nome, produto.preco, produto.descricao)

    }

    override fun delete(idProduto: String) {

        var produto = produtoRepository.findByIdProduto(idProduto).orElseThrow {
            throw ProdutoNotFoundException("Produto não encontrada com id ${idProduto} informado")
        }
        produtoRepository.delete(produto)
    }

}