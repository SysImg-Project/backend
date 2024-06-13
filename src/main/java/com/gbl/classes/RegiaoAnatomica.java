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
// - Deve receber uma String em código do regiao ou por extenso e definir se ela existe
// - Se não existe deve realizar um 'throw'
// - Armazena apenas um inteiro na DB (usa um TINYINT) representando o regiao escolhido
// - Deve retornar (diferentes métodos get):
//      - o inteiro que representa o regiao
//      - o código de 2 letras maiúsculas que representa o regiao
//      - o nome por extenso do regiao

// Define os campos de informação de um Regiao de Imagem
//private class RegiaoInfo { //modifier private not allowed here
class RegiaoAnatomicaInfo {
    public String sigla;
    public String nomeExtenso;

    public RegiaoAnatomicaInfo(String sigla, String nomeExtenso) {
        this.sigla = sigla;
        this.nomeExtenso = nomeExtenso;
    }

    // Não criei os métodos get/set aqui porque esta classe é private e só vai ser utilizada pela classe abaixo
    // Esta classe RegiaoInfo poderia ser chamada de uma classe auxiliar
}


public class RegiaoAnatomica {
    private int codigoRegiao; // 0 = INDEFINIDO
    private String[][] regiaosArr; // {"RS", "Rio Grande do Sul"}, // 27

    //private Map<int, RegiaoInfo> regiaosMapa; // unexpected type [ERROR]   required: reference [ERROR]   found:    int
// Escolhi assim e não Map<String("RS"), RegiaoInfo> porque agiliza na pesquisa de um código de regiao quando recupera o códigoRegiao da DB -- pensando bem não tem muita utilidade um mapa assim com chave usando um int. Isso poderia ser feito com um vetor ou até com um array mesmo ---> Vou usar o array 'regiaosArr'

    public RegiaoAnatomica() { // Construtor vazio com valores seguros
        populaMapaRegiaos();
        codigoRegiao = 0; // Sempre inicializa com um valor mesmo que seja 0
    }

    public RegiaoAnatomica(String regiao) {
        populaMapaRegiaos();
        validaRegiao(regiao);
    }

    public void populaMapaRegiaos() {
        //regiaosArr = { // illegal start of expression
        String[][] regiaosArr = {
            {"IN", "INDEFINIDO"}, // 0
            {"CR", "Crânio"}, // 1
            {"SF", "Seios da face"}, // 1
            {"PES", "Pescoço"}, //
            {"TO", "Tórax"}, //
            {"ABD", "Abdômen"}, //
            {"PEL", "Pelve"}, //
            {"MSSS", "Membros superiores"}, //
            {"MSD", "Membro superior direito"}, //
            {"MSE", "Membro superior esquerdo"}, //
            {"BD", "Braço direito"}, //
            {"BE", "Braço esquerdo"}, //
            {"CD", "Cotovelo direito"}, //
            {"CE", "Cotovelo esquerdo"}, //
            {"AD", "Antebraço direito"}, //
            {"AE", "Antebraço esquerdo"}, //
            {"PUD", "Punho direito"}, //
            {"PUE", "Punho esquerdo"}, //
            {"MD", "Mão direita"}, //
            {"ME", "Mão esquerda"}, //

            {"MSIS", "Membros inferiores"}, //
            {"MID", "Membro inferior direito"}, //
            {"MIE", "Membro inferior esquerdo"}, //
            {"CXD", "Coxa direita"}, //
            {"CXE", "Coxa esquerda"}, //
            {"JD", "Joelho direito"}, //
            {"JE", "Joelho esquerdo"}, //
            {"PND", "Perna direita"}, //
            {"PNE", "Perna esquerda"}, //
            {"TD", "Tornozelo direito"}, //
            {"TE", "Tornozelo esquerdo"}, //
            {"PED", "Pé direito"}, //
            {"PEE", "Pé esquerdo"}, //


            //{"", ""}, //
        };

        this.regiaosArr = new String[regiaosArr.length][2];

        for (int i = 0; i < regiaosArr.length; i++) {
            //this.regiaosArr[i][0] = regiaosArr[i][0];
            //this.regiaosArr[i][1] = regiaosArr[i][1];
            this.regiaosArr[i] = regiaosArr[i]; // Poderia ser assim ?
            /*RegiaoInfo ei = new RegiaoInfo(regiaosArr[i][0], regiaosArr[i][1]); // Cria um objeto RegiaoInfo
            regiaosMapa.put(i, ei); // Põe no mapa*/ // Deletar ?
        }
    }

    public void validaRegiao(String regiao) throws ExcecaoValorInvalido {
        for (int i = 0; i < regiaosArr.length; i++) {
            // Procura pela sigla:
            if (regiaosArr[i][0].equals(regiao)) {
                codigoRegiao = i;
                return;
            }
            // Procura pelo nome em extenso:
            if (regiaosArr[i][1].equals(regiao)) {
                codigoRegiao = i;
                return;
            }
        }
        // Se chegou até aqui significa que não encontrou uma sigla ou nome de regiao válido --> gera um throw
        throw new ExcecaoValorInvalido("A string informada para indicar um região anatômica não é válida nem na busca por siglas, bem como na busca por nome por extenso.\nString informada ="+regiao+".\n");
    }

    public void setCodigoRegiao(int codigoRegiao) throws ExcecaoValorInvalido {
        // Avalia se este código está dentro dos valores aceitos
        // Valores 0 -> 28 -- regiaosArr = new String[28][2];
        if ((codigoRegiao < 0) || (codigoRegiao >= regiaosArr.length)) { // Fora da faixa --> throw
            throw new ExcecaoValorInvalido("O inteiro informado para indicar um região anatômica não está dentro da faixa de valores válidos que vai de 0 à "+regiaosArr.length+".\nint informado ="+codigoRegiao+".\n");
        }
        else {
            this.codigoRegiao = codigoRegiao;
        }
    }

    public int getCodigoRegiao() {
        return codigoRegiao;
    }

    public void setCodigoRegiaoSigla(String regiao) {
        validaRegiao(regiao);
    }

    public String getCodigoRegiaoSigla() {
        //return regiaosMapa.get(codigoRegiao).sigla;
        return regiaosArr[codigoRegiao][0];
    }

    public void setCodigoRegiaoExtenso(String regiao) {
        validaRegiao(regiao);
    }

    public String getCodigoRegiaoExtenso() {
        //return regiaosMapa.get(codigoRegiao).nomeExtenso;
        return regiaosArr[codigoRegiao][1];
    }


}
