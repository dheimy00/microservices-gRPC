package br.com.dheimy00.repository

import br.com.dheimy00.model.Cliente
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface ClienteRepository : JpaRepository<Cliente,Long> {

    fun findByIdCliente(idCliente: String): Optional<Cliente>
}