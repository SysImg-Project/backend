// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 12/05/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl;
import org.json.*; // Para JSON

public class RetornoCrud {
    private boolean sucesso;
    private int inteiro; // Qunado inserir um registro é interessante saber o número da id onde foi inserido -- não está no construtor pois não é obrigatório
    private String msg;
    JSONObject json;

    public RetornoCrud(boolean sucesso, String msg, JSONObject json) {
        this.sucesso = sucesso;
        this.msg = msg;
        this.json = json;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public boolean getSucesso() {
        return this.sucesso;
    }

    public void setInteiro(int inteiro) {
        this.inteiro = inteiro;
    }

    public int getInteiro() {
        return this.inteiro;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public JSONObject getJson() {
        return this.json;
    }


}
