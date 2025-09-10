package crud

import ProjetoCaixadAgua.Entidades.CaixaDAgua
import ProjetoCaixadAgua.Enum.MaterialCaixa
import ProjetoCaixadAgua.Enum.Corcaixa
import java.sql.ResultSet

val conectar = EntidadeJDBC(
    url = "jdbc:postgresql://localhost:5433/GerenciamentoDeCaixaDAgua",
    usuario = "postgres",
    senha = "1313"
)

fun criarTabelaCaixa(){


    val sql = "CREATE TABLE IF NOT EXISTS CaixaDAgua " +
            " (id serial NOT NULL PRIMARY KEY," +
            " material varchar(255)," + //Enumeradores serão STRINGS no banco
            " capacidade float," +
            " cor varchar(255)," +
            " peso float," +
            " preco varchar(255)," +
            " altura float," +
            " largura float," +
            " profundidade float" +
            ")"

    val banco = conectar.conectarComBanco()
    val enviarParaBanco = banco!!.createStatement().execute(sql)

    println(enviarParaBanco)//Se retornar false ou 1, deu certo!

    banco.close()//encerra a conexão
}

fun cadastrarCaixa(){
    /*
    val material : MaterialCaixa,
    val capacidade : Int,
    val cor : Corcaixa,
    val peso : Double,
    val preco : BigDecimal?,
    val altura : Double,
    val largura: Double,
    val profundidade : Double,
    PLASTICO, CONCRETO, METAL, ARGAMASSA
        PRETO, AZUL, BRANCO
     */
    println("Escolha o material do qual a caixa é composta:")
    println("1 - Plástico")
    println("2 - Concreto")
    println("3 - Metal")
    println("4 - Argamassa")

    val opcao = readln().toInt()
    var material : MaterialCaixa
    when(opcao){
        1-> material = MaterialCaixa.PLASTICO
        2-> material = MaterialCaixa.CONCRETO
        3-> material = MaterialCaixa.METAL
        4-> material = MaterialCaixa.ARGAMASSA
        else -> material = MaterialCaixa.PLASTICO
    }

    println("Capacidade da caixa em litros:")
    val litros = readln().toInt()

    var cor : Corcaixa
    when(opcao){
        1-> cor = Corcaixa.AZUL
        2-> cor = Corcaixa.PRETO
        3-> cor = Corcaixa.BRANCO
        else -> cor = Corcaixa.AZUL
    }

    println("Fale o peso:")
    val peso = readln().toDouble()

    println("Preço da caixa:")
    val preco = readln().toBigDecimal()

    println("Altura da caixa:")
    val altura = readln().toDouble()

    println("Largura da caixa:")
    val largura = readln().toDouble()

    println("Profundidade da caixa:")
    val profundidade = readln().toDouble()




    val c = CaixaDAgua(
        material = material,
        capacidade = litros,
        cor = cor,
        preco = preco,
        altura = altura,
        largura = largura,
        peso = peso,
        profundidade = profundidade
    )

    val banco = conectar.conectarComBanco()!!.prepareStatement(
        "INSERT INTO CaixaDAgua" +
                " (material, capacidade, cor, preco, altura, largura, peso, profundidade)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"

    )
        banco.setString(1, c.material.name)//enums
        banco.setInt(2, c.capacidade!!)
        banco.setString(3, c.cor.toString())
        banco.setString(4, c.preco.toString())
        banco.setDouble(5, c.altura)
        banco.setDouble(6, c.largura)
        banco.setDouble(7, c.profundidade)
        banco.setDouble(8, c.largura)

        banco.executeUpdate()//isso fara commit no banco

        banco.close()//fecha a transaçao e a conexão com o banco



}

fun editarCaixa(){

}

fun listarCaixas(){
    val banco = conectar.conectarComBanco()
    val sql = "SELECT * FROM CaixaDAgua"
    //apos consultar um banco usando SQL junto da função executeQuery
    //A consulta, se assertiva , retorna um array de respostas
    val resultados : ResultSet = banco!!.createStatement().executeQuery(sql)

    while(resultados.next()){
        println("------------------------------------------------------------------")
        println("Id: ${resultados.getString("id")}")
        println("Material: ${resultados.getString("material")}")
        println("Capacidade: ${resultados.getDouble("capacidade")}")
        println("Cor: ${resultados.getString("cor")}")
        println("Peso: ${resultados.getDouble("peso")}")
        println("Preco: ${resultados.getString("preco")}")
        println("Altura: ${resultados.getDouble("altura")}")
        println("Largura: ${resultados.getDouble("largura")}")
        println("Profundidade: ${resultados.getDouble("profundidade")}")
    }
}

fun excluirCaixa(){
    println("digite o id da tabela que deseja excluir")
    val id = readln().toInt()

    val banco = conectar.conectarComBanco()
    val sqlBusca = "SELECT * FROM CaixaDAgua WHERE id = ?"
    val resultados = banco!!.prepareStatement(sqlBusca)
    resultados.setInt(1, id)
    val retorno = resultados.executeQuery()
    while (retorno.next()) {
        println("------------------------------------------------------------------")
        println("Id: ${retorno.getInt("id")}")
        println("Material: ${retorno.getString("material")}")
        println("Capacidade: ${retorno.getDouble("capacidade")}")
        println("Cor: ${retorno.getString("cor")}")
        println("Peso: ${retorno.getDouble("peso")}")
        println("Preco: ${retorno.getString("preco")}")
        println("Altura: ${retorno.getDouble("altura")}")
        println("Largura: ${retorno.getDouble("largura")}")
        println("Profundidade: ${retorno.getDouble("profundidade")}")
    }
    println("Tem Certeza que quer excluir esse Registro?")
    val resposta = readln().lowercase()
    when(resposta){
        "sim"-> {
            val deletar = banco.prepareStatement("DELETE FROM CaixaDAgua WHERE id = ?")
                deletar.setInt(1, id)
                 deletar.executeUpdate()
        }
        else -> {
            println("Operação Cancelada")
        }
    }
}
