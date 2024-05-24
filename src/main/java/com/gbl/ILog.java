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

interface ILog {

    public void log(String msg); // Imprime no console e/ou arquivo
    public void logN(String msg); // Imprime no console e/ou arquivo com um 'newline' no final

    public void deb(String msg);
    public void debN(String msg);

    public void db(String msg);
    public void dbN(String msg);

    public void aviso(String msg);
    public void avisoN(String msg);

    public void erro(String msg);
    public void erroN(String msg);

}
