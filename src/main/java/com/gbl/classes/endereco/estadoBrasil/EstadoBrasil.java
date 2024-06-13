// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 25/05/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.classes.endereco.estadoBrasil;
//import java.util.Map; // Não está sendo usado -- deletar ?
import com.gbl.classes.ExcecaoValorInvalido;


// Objetivo:
// - Deve receber uma String em código do estado ou por extenso e definir se ela existe
// - Se não existe deve realizar um 'throw'
// - Armazena apenas um inteiro na DB (usa um TINYINT) representando o estado escolhido
// - Deve retornar (diferentes métodos get):
//      - o inteiro que representa o estado
//      - o código de 2 letras maiúsculas que representa o estado
//      - o nome por extenso do estado

// Define os campos de informação de um Estado Brasileiro
//private class EstadoInfo { //modifier private not allowed here
class EstadoInfo {
    public String sigla;
    public String nomeExtenso;

    public EstadoInfo(String sigla, String nomeExtenso) {
        this.sigla = sigla;
        this.nomeExtenso = nomeExtenso;
    }

    // Não criei os métodos get/set aqui porque esta classe é private e só vai ser utilizada pela classe abaixo
    // Esta classe EstadoInfo poderia ser chamada de uma classe auxiliar
}


public class EstadoBrasil {
    private int codigoEstado; // 0 = INDEFINIDO
    private String[][] estadosArr; // {"RS", "Rio Grande do Sul"}, // 27

    //private Map<int, EstadoInfo> estadosMapa; // unexpected type [ERROR]   required: reference [ERROR]   found:    int
// Escolhi assim e não Map<String("RS"), EstadoInfo> porque agiliza na pesquisa de um código de estado quando recupera o códigoEstado da DB -- pensando bem não tem muita utilidade um mapa assim com chave usando um int. Isso poderia ser feito com um vetor ou até com um array mesmo ---> Vou usar o array 'estadosArr'

    public EstadoBrasil() { // Construtor vazio com valores seguros
        populaMapaEstados();
        codigoEstado = 0; // Sempre inicializa com um valor mesmo que seja 0
    }

    public EstadoBrasil(String estado) {
        populaMapaEstados();
        validaEstado(estado);
    }

    public void populaMapaEstados() {
        //estadosArr = { // illegal start of expression
        String[][] estadosArr = {
            {"IN", "INDEFINIDO"}, // 0
            {"AC", "Acre"}, // 1
            {"AL", "Alagoas"}, // 2
            {"AM", "Amazonas"}, // 3
            {"AP", "Amapá"}, // 4
            {"BA", "Bahia"}, // 5
            {"CE", "Ceará"}, // 6
            {"DF", "Distrito Federal"}, // 7
            {"ES", "Espírito Santo"}, // 8
            {"GO", "Goiás"}, // 9
            {"MA", "Maranhão"}, // 10
            {"MT", "Mato Grosso"}, // 11
            {"MS", "Mato Grosso do Sul"}, // 12
            {"MG", "Minas Gerais"}, // 13
            {"PA", "Pará"}, // 14
            {"PB", "Paraíba"}, // 15
            {"PE", "Pernambuco"}, // 16 -- Fernando de Noronha é um arquipélago brasileiro do estado de Pernambuco -- não é estado nem território !
            {"PI", "Piauí"}, // 17
            {"PR", "Paraná"}, // 18
            {"RE", "Recife"}, // 19
            {"RR", "Roraima"}, // 20
            {"RO", "Rondônia"}, // 21
            {"RJ", "Rio de Janeiro"}, // 22
            {"RN", "Rio Grande do Norte"}, // 23
            {"SE", "Sergipe"}, // 24
            {"SC", "Santa Catarina"}, // 25
            {"SP", "São Paulo"}, // 26
            {"RS", "Rio Grande do Sul"}, // 27
            {"TO", "Tocantins"}, // 28
        };

        this.estadosArr = new String[estadosArr.length][2];

        for (int i = 0; i < estadosArr.length; i++) {
            //this.estadosArr[i][0] = estadosArr[i][0];
            //this.estadosArr[i][1] = estadosArr[i][1];
            this.estadosArr[i] = estadosArr[i]; // Poderia ser assim ?
            /*EstadoInfo ei = new EstadoInfo(estadosArr[i][0], estadosArr[i][1]); // Cria um objeto EstadoInfo
            estadosMapa.put(i, ei); // Põe no mapa*/ // Deletar ?
        }
    }

    public void validaEstado(String estado) throws ExcecaoValorInvalido {
        for (int i = 0; i < estadosArr.length; i++) {
            // Procura pela sigla:
            if (estadosArr[i][0].equals(estado)) {
                codigoEstado = i;
                return;
            }
            // Procura pelo nome em extenso:
            if (estadosArr[i][1].equals(estado)) {
                codigoEstado = i;
                return;
            }
        }
        // Se chegou até aqui significa que não encontrou uma sigla ou nome de estado válido --> gera um throw
        throw new ExcecaoValorInvalido("A string informada para indicar um estado brasileiro não é válida nem na busca por siglas, bem como na busca por nome por extenso.\nString informada ="+estado+".\n");
    }

    public void setCodigoEstado(int codigoEstado) throws ExcecaoValorInvalido {
        // Avalia se este código está dentro dos valores aceitos
        // Valores 0 -> 28 -- estadosArr = new String[28][2];
        if ((codigoEstado < 0) || (codigoEstado >= estadosArr.length)) { // Fora da faixa --> throw
            throw new ExcecaoValorInvalido("O inteiro informado para indicar um estado brasileiro não está dentro da faixa de valores válidos que vai de 0 à "+estadosArr.length+".\nint informado ="+codigoEstado+".\n");
        }
        else {
            this.codigoEstado = codigoEstado;
        }
    }

    public int getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstadoSigla(String estado) {
        validaEstado(estado);
    }

    public String getCodigoEstadoSigla() {
        //return estadosMapa.get(codigoEstado).sigla;
        return estadosArr[codigoEstado][0];
    }

    public void setCodigoEstadoExtenso(String estado) {
        validaEstado(estado);
    }

    public String getCodigoEstadoExtenso() {
        //return estadosMapa.get(codigoEstado).nomeExtenso;
        return estadosArr[codigoEstado][1];
    }


}
