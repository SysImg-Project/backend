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

/*
Classe responsável por gerenciar chaves de configuração do aplicativo
Usa o padrão de projeto Singleton
*/

//public enum Modo { // Para que a enum Modo não precise ser declarada em seu próprio arquivo removi o 'public' já que aparentemente ela só vai ser usada aqui na classe 'Config'
enum Modo {
    INDEFINIDO, DESENVOLVIMENTO, TESTE, PRODUCAO;
    /*private valor;

    @Override
    public String toString() {
        switch()
    }*/
}

public final class Config {
    private static Config INSTANCE; // Todo o Singleton deve ter um campo estático que contêm a única instância

    private Log log; // Instância do objeto Log -- para logs

    private Modo modo = Modo.DESENVOLVIMENTO; // Modo Padrão

    //private String info = "Initial info class";

    private Config() { // Todo o Singleton deve ter um construtor privado -- só acessível através do método getInstance()
        this.log = Log.getInstance(); // Obtêm uma instância do objeto Log -- para logs
    }

    public static Config getInstance() { // Todo o Singleton deve conter um método fábrica estático que permita obter a instância
        if(INSTANCE == null) {
            INSTANCE = new Config();
        }

        return INSTANCE;
    }

    // Imprime no console o conteúdo de System.getenv()
    public static void imprimirGetenv() {
        //public static Map<String,String> System.getenv()
        Map<String, String> map = System.getenv();
        System.out.println("System.getenv():");
        for (String key : map.keySet()) {
            System.out.println(key + ":" + map.get(key));
        }
    }

    // Aqui deve chamar o singleton DB
    public void executarRotinasInicializacao() {
        // Inicializa o Singleton DB -- fornece uma conexão com a DB via JDBC

    }

    public void setModo(Modo modo){
        this.modo = modo;
    }

    public Modo getModo() {
        return this.modo;
    }



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
    public String toStringModo() {
        switch(this.modo) {
            //case Modo.DESENVOLVIMENTO: return "Modo.DESENVOLVIMENTO"; // an enum switch case label must be the unqualified name of an enumeration constant --> Retirar o 'Modo' do case.
            case DESENVOLVIMENTO: return "DESENVOLVIMENTO";
            case TESTE: return "TESTE";
            case PRODUCAO: return "PRODUCAO";
            case INDEFINIDO:
            default: return "INDEFINIDO";
            //Aparentemente poderia fazer: default: return Modo.INDEFINIDO; -- sem as aspas -- converte automaticamente para String -- será ?
        }
    }

    @Override
    public String toString() {
        return "Config: [modo: "+toStringModo()+"]";
    }
}
