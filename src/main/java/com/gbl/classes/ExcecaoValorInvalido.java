// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 25/05/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.classes;

import java.lang.RuntimeException;

public class ExcecaoValorInvalido extends RuntimeException {
    private String msg;

    public ExcecaoValorInvalido(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return "###Exceção: -"+msg;
    }
}
