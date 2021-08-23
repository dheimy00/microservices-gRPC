package br.com.dheimy00.repository

import br.com.dheimy00.model.Pedido
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface PedidoRepository: JpaRepository<Pedido,Long>  {

    fun findByIdPedido(idPedido: String): Optional<Pedido>
}