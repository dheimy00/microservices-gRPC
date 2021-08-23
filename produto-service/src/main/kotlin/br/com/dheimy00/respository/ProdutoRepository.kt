package br.com.dheimy00.respository

import br.com.dheimy00.model.Produto
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface ProdutoRepository: JpaRepository<Produto,Long> {

    fun findByNome(nome: String): Optional<Produto>
    fun findByIdProduto(idProduto: String): Optional<Produto>
}