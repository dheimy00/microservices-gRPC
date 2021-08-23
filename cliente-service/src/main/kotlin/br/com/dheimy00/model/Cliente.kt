package br.com.dheimy00.model

import javax.persistence.*

@Entity
data class Cliente(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long?,

    @Column(name = "id_cliente", updatable = false, unique = true, nullable = false)
    var idCliente: String,

    @Column(name = "nome")
    var nome: String,

    @Column(name = "cpf")
    var cpf:String
){}