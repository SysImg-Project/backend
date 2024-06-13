// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 02/06/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.classes;
import com.gbl.classes.ExcecaoValorInvalido;


// Objetivo:
// - Deve receber uma String em código do exame ou por extenso e definir se ela existe
// - Se não existe deve realizar um 'throw'
// - Armazena apenas um inteiro na DB (usa um TINYINT) representando o exame escolhido
// - Deve retornar (diferentes métodos get):
//      - o inteiro que representa o exame
//      - o código de 2 letras maiúsculas que representa o exame
//      - o nome por extenso do exame

// Define os campos de informação de um Exame de Imagem
//private class ExameInfo { //modifier private not allowed here
class ExameImagemInfo {
    public String sigla;
    public String nomeExtenso;

    public ExameImagemInfo(String sigla, String nomeExtenso) {
        this.sigla = sigla;
        this.nomeExtenso = nomeExtenso;
    }

    // Não criei os métodos get/set aqui porque esta classe é private e só vai ser utilizada pela classe abaixo
    // Esta classe ExameInfo poderia ser chamada de uma classe auxiliar
}


public class ExameImagemTipo {
    private int codigoExame; // 0 = INDEFINIDO
    private String[][] examesArr; // {"RS", "Rio Grande do Sul"}, // 27

    //private Map<int, ExameInfo> examesMapa; // unexpected type [ERROR]   required: reference [ERROR]   found:    int
// Escolhi assim e não Map<String("RS"), ExameInfo> porque agiliza na pesquisa de um código de exame quando recupera o códigoExame da DB -- pensando bem não tem muita utilidade um mapa assim com chave usando um int. Isso poderia ser feito com um vetor ou até com um array mesmo ---> Vou usar o array 'examesArr'

    public ExameImagemTipo() { // Construtor vazio com valores seguros
        populaMapaExames();
        codigoExame = 0; // Sempre inicializa com um valor mesmo que seja 0
    }

    public ExameImagemTipo(String exame) {
        populaMapaExames();
        validaExame(exame);
    }

    public void populaMapaExames() {
        //examesArr = { // illegal start of expression
        String[][] examesArr = {
            {"IN", "INDEFINIDO"}, // 0
            {"RX", "Raio X"}, // 1
            {"TC", "Tomografia Computadorizada"}, // 2
            {"ECO", "Ecografia"}, // 3
            {"ECG", "Eletrocardiograma"}, // 4
        };

        this.examesArr = new String[examesArr.length][2];

        for (int i = 0; i < examesArr.length; i++) {
            //this.examesArr[i][0] = examesArr[i][0];
            //this.examesArr[i][1] = examesArr[i][1];
            this.examesArr[i] = examesArr[i]; // Poderia ser assim ?
            /*ExameInfo ei = new ExameInfo(examesArr[i][0], examesArr[i][1]); // Cria um objeto ExameInfo
            examesMapa.put(i, ei); // Põe no mapa*/ // Deletar ?
        }
    }

    public void validaExame(String exame) throws ExcecaoValorInvalido {
        for (int i = 0; i < examesArr.length; i++) {
            // Procura pela sigla:
            if (examesArr[i][0].equals(exame)) {
                codigoExame = i;
                return;
            }
            // Procura pelo nome em extenso:
            if (examesArr[i][1].equals(exame)) {
                codigoExame = i;
                return;
            }
        }
        // Se chegou até aqui significa que não encontrou uma sigla ou nome de exame válido --> gera um throw
        throw new ExcecaoValorInvalido("A string informada para indicar um exame de imagem não é válida nem na busca por siglas, bem como na busca por nome por extenso.\nString informada ="+exame+".\n");
    }

    public void setCodigoExame(int codigoExame) throws ExcecaoValorInvalido {
        // Avalia se este código está dentro dos valores aceitos
        // Valores 0 -> 28 -- examesArr = new String[28][2];
        if ((codigoExame < 0) || (codigoExame >= examesArr.length)) { // Fora da faixa --> throw
            throw new ExcecaoValorInvalido("O inteiro informado para indicar um exame de imagem não está dentro da faixa de valores válidos que vai de 0 à "+examesArr.length+".\nint informado ="+codigoExame+".\n");
        }
        else {
            this.codigoExame = codigoExame;
        }
    }

    public int getCodigoExame() {
        return codigoExame;
    }

    public void setCodigoExameSigla(String exame) {
        validaExame(exame);
    }

    public String getCodigoExameSigla() {
        //return examesMapa.get(codigoExame).sigla;
        return examesArr[codigoExame][0];
    }

    public void setCodigoExameExtenso(String exame) {
        validaExame(exame);
    }

    public String getCodigoExameExtenso() {
        //return examesMapa.get(codigoExame).nomeExtenso;
        return examesArr[codigoExame][1];
    }


}
