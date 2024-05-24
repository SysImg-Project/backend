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

/*
Classe responsável por gerenciar o sistema de log do aplicativo
Usa o padrão de projeto Singleton
*/

public final class Log implements ILog{
    private static Log INSTANCE; // Todo o Singleton deve ter um campo estático que contêm a única instância
    private boolean imprimirNoConsole = false;
    private boolean imprimirNoArquivo = false;
    String pathArquivo = "";
    String nomeArquivo = "";


    private Log(boolean imprimirNoConsole, boolean imprimirNoArquivo) { // Todo o Singleton deve ter um construtor privado -- só acessível através do método getInstance()
        this.imprimirNoConsole = imprimirNoConsole;
        this.imprimirNoArquivo = imprimirNoArquivo;
    }

    public static Log getInstance() { // Todo o Singleton deve conter um método fábrica estático que permita obter a instância
        if(INSTANCE == null) {
            //INSTANCE = new Log(false, false);
            INSTANCE = new Log(true, false); // Impressão só no console
        }

        return INSTANCE;
    }

    @Override
    public void log(String msg) {
        if (this.imprimirNoConsole) {
            System.out.print("Gbl-LOG: "+msg);
        }
        if (this.imprimirNoArquivo) {
        }
    }

    // Realiza log com 'newline (\n)' no final da mensagem
    @Override
    public void logN(String msg) {
        msg += "\n";
        this.log(msg);
    }

    @Override
    public void deb(String msg) {
        if (this.imprimirNoConsole) {
            System.out.print("Gbl-DEB: "+msg);
        }
        if (this.imprimirNoArquivo) {
        }
    }

    @Override
    public void debN(String msg) {
        msg += "\n";
        this.deb(msg);
    }


    @Override
    public void db(String msg) {
        if (this.imprimirNoConsole) {
             System.out.print("Gbl-DB: "+msg);
        }
        if (this.imprimirNoArquivo) {
        }
    }

    // Realiza log com 'newline (\n)' no final da mensagem
    @Override
    public void dbN(String msg) {
        msg += "\n";
        this.db(msg);
    }

    @Override
    public void aviso(String msg) {
        if (this.imprimirNoConsole) {
             System.out.print("Gbl-AVISO: "+msg);
        }
        if (this.imprimirNoArquivo) {
        }
    }

    // Realiza log com 'newline (\n)' no final da mensagem
    @Override
    public void avisoN(String msg) {
        msg += "\n";
        this.aviso(msg);
    }

    @Override
    public void erro(String msg) {
        if (this.imprimirNoConsole) {
             System.out.print("Gbl-ERRO: "+msg);
        }
        if (this.imprimirNoArquivo) {
        }
    }

    // Realiza log com 'newline (\n)' no final da mensagem
    @Override
    public void erroN(String msg) {
        msg += "\n";
        this.erro(msg);
    }

    /*@Override
    public void #(String msg) {
        if (this.imprimirNoConsole) {
        }
        if (this.imprimirNoArquivo) {
        }
    }

    // Realiza log com 'newline (\n)' no final da mensagem
    @Override
    public void #N(String msg) {
        msg += "\n";
        this.#(msg);
    }*/


/*    public void set#(String #){
        this.# = #;
    }

    public String get#() {
        return this.#;
    }

    public void set#(String #){
        this.# = #;
    }

    public String get#() {
        return this.#;
    }
*/

    @Override
    public String toString() {
        return "Log: [imprimirNoConsole: "+this.imprimirNoConsole+"...]";
    }
}

