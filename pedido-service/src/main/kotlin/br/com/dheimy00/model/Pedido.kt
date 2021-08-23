package br.com.dheimy00.model

import java.time.LocalDate
import javax.persistence.*

@Entity
data class Pedido(

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id")
   var id:Long?,

   @Column(name = "idPedido")
   var idPedido: String,

   @Column(name = "preco_total")
   var precoTotal: Double,

   @Column(name = "quantidade")
   var quantidade: Int,

   @Column(name = "idCliente")
   var idCliente: String,

   @Column(name = "idProduto")
   var idProduto: String,

) {}