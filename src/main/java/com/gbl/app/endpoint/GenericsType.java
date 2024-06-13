// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 26/05/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.app.endpoint;

public class GenericsType<T> {
	private T t;

	public T get(){
		return this.t;
	}

	public void set(T t1){
		this.t=t1;
	}
}
