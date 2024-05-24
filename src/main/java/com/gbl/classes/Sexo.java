// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 28/04/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.classes;

public enum Sexo {
INDEFINIDO(0), MASCULINO(1), FEMININO(2), OUTRO(3);
    private int valor;

    Sexo(int _valor) {
        this.valor = _valor;
    }

    int getValor() {
        return this.valor;
    }

    public String toString() {
        switch(this.valor) {
            //case 0: return "INDEFINIDO";
            case 1: return "MASCULINO";
            case 2: return "FEMININO";
            case 3: return "OUTRO";
            default:
            case 0: return "INDEFINIDO";
        }

        //https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Enum.html
        // public final String name()
        //this.valor.name(); // Não sei se está correto !!!
    }

}
