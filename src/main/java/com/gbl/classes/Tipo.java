// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 14/05/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.classes;

public enum Tipo {
INDEFINIDO(0),
RECEPCIONISTA(1),
TECNICO_DE_ENFERMAGEM(2),
TECNICO_DE_RADIOLOGIA(3),
ENFERMEIRO(4),
MEDICO(5),
PACIENTE(6),
DESENVOLVEDOR(99);
    private int valor;

    Tipo(int _valor) {
        this.valor = _valor;
    }

    int getValor() {
        return this.valor;
    }

    /*public String toString() {
        switch(this.valor) {
            //case 0: return "INDEFINIDO";
            case 1: return "MASCULINO";
            case 2: return "FEMININO";
            case 3: return "OUTRO";
            default:
            case 0: return this.;
        }

        //https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Enum.html
        // public final String name()
        //this.valor.name(); // Não sei se está correto !!!
    }*/ // DEL -- redundante -- existe na própria classe Enum do Java o método: String toString() Returns the name of this enum constant, as contained in the declaration.


}
