// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 01/06/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl;

// Interface que deve ser seguida por todas as classes que forem representar modelos de objetos (Ex.: Pessoa, Paciente, Usuario) que forem precisar do armazenamento dos mesmos na DB
public interface ITable {
    //String getTabelaNome(); // Mostra o nome da tabela que está representando este modelo na DB

    //Map<String, String> obterCampos(); // Mostra em um mapa o nome dos campos e suas características

    RetornoCrud createTable();
    RetornoCrud deleteTable();

}
