package ui
import crud.cadastrarCaixa

fun menu(){
    do {
        println("1 - Cadastrar Caixa D'Água")
        println("2 - Editar Caixa D'Água")
        println("3 - Listar Caixa D'Água")
        println("4 - Excluir Caixa D'Água")
        println("0 - Sair")

        var opcao = readln().toInt()
        when(opcao){
            0-> println("Saiu")
            1-> cadastrarCaixa()
            2-> println("Editando caixa...")
            3-> println("Listar caixas...")
            4-> println("Excluindo caixa...")
            else -> println("Opção inválida!")
        }
    } while (opcao != 0)


}
