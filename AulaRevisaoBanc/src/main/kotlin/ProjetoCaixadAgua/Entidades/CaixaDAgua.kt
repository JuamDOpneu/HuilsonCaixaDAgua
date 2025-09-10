package ProjetoCaixadAgua.Entidades

import ProjetoCaixadAgua.Enum.Corcaixa
import ProjetoCaixadAgua.Enum.MaterialCaixa
import java.math.BigDecimal

class CaixaDAgua (
    //val id : Int,
    val material : MaterialCaixa,
    val capacidade : Int,
    val cor : Corcaixa,
    val peso : Double,
    val preco : BigDecimal?,
    val altura : Double,
    val largura: Double,
    val profundidade : Double,

)

