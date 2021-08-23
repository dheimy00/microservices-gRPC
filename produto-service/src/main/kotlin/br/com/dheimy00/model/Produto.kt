package br.com.dheimy00.model


import javax.persistence.*

@Entity
data class Produto(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "id_produto", updatable = false, unique = true, nullable = false)
    var idProduto : String,

    @Column(name = "identificadorProduto",nullable = false)
    var identificadorProduto : String,

    @Column(name = "nome",nullable = false)
    var nome: String,

    @Column(name = "preco",nullable = false)
    var preco: Double,

    @Column(name = "descricao",nullable = false)
    var descricao: String,

) {}
