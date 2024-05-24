// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 28/04/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl;
import java.util.Map;

interface IDB {

    //public abstract void criarTabela(String tabela, Map<String, String> campos); // Não precisa colocar 'public' e nem 'abstract' pois por definição todos os métodos das interface já estão definidos assim na linguagem

    void criarTabela(String tabela, Map<String, String> campos); // Cria a tabela solicitada no parâmetro com os campos da lista
    void deletarTabela(String tabela); // Deleta a tabela indicada

    int inserirNovoRegistro(String tabela, Map<String, String> valores);// Insere um NOVO registro na tabela indicada com os valores dos campos solicitados e retorna o valor do índice criado

    String[][] obterRegistros(String tabela, String[] campos, String condicao); // Obtém registros da tabela solicitada, recebendo o resultado em uma matriz com os campos solicitados seguindo a condicao estabelecida

    }
