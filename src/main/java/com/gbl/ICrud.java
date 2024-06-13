// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 11/05/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl;

// Interface que deve ser seguida por todas as classes que forem representar modelos de objetos (Ex.: Pessoa, Paciente, Usuario) que forem precisar do armazenamento dos mesmos na DB
public interface ICrud {
    RetornoCrud create(); // 'int' Pq retorna o número do índice inserida na tabela
    RetornoCrud read(int id);
    RetornoCrud update(int id);
    RetornoCrud delete(int id);
    RetornoCrud deleteAll();
    RetornoCrud list(String condicao);
}
