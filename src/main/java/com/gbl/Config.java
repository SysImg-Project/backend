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

    //private Modo modo = Modo.DESENVOLVIMENTO; // Modo Padrão
    private Modo modo = Modo.PRODUCAO; // Modo Producao
    private String[] allowedOrigins; // Endereço do Teste de Backend do SysImg

    //private String info = "Initial info class";

    private Config() { // Todo o Singleton deve ter um construtor privado -- só acessível através do método getInstance()
        this.log = Log.getInstance(); // Obtêm uma instância do objeto Log -- para logs
        this.allowedOrigins = new String[10]; // Precisa inicializar aqui senão não vai ter como acessar esse método no runtime -- erro não avisado na compilação
    }

    public static Config getInstance() { // Todo o Singleton deve conter um método fábrica estático que permita obter a instância
        if(INSTANCE == null) {
            INSTANCE = new Config();
        }
        configuraVariaveisConformeModo(); // Chama aqui porque a INSTANCE já deve estar definida (não nula !)
        return INSTANCE;
    }

    private static void configuraVariaveisConformeModo() {
        // Verifica o tipo de modo de execução conforme o que estiver no atributo 'modo' da classe Config:
        switch(INSTANCE.toStringModo()) {
            case "PRODUCAO":
                //allowedOrigins = {"http://168.0.126.147:65000"}; illegal start of expression tem que usar [0] = "" -- não pode usar o construtor de definição "{}" aqui
                INSTANCE.allowedOrigins[0] = "http://168.0.126.147:65000"; // Endereço do Teste de Backend do SysImg - PRODUÇÃO
                INSTANCE.allowedOrigins[1] = "http://192.168.0.20:5000"; // Intranet Casa do Cris
                INSTANCE.allowedOrigins[2] = "http://192.168.1.20:5000"; // Intranet Minha Casa
                INSTANCE.allowedOrigins[3] = "http://195.35.43.63:5000"; // Hostinger
                INSTANCE.allowedOrigins[4] = "http://127.0.0.1:5000"; // Localhost
                break;
            case "TESTE":
                INSTANCE.allowedOrigins[0] = "http://168.0.126.147:65200"; // Endereço do Teste de Backend do SysImg - TESTE
                break;
            case "DESENVOLVIMENTO":
            case "INDEFINIDO":
            default:
                INSTANCE.allowedOrigins[0] = "http://168.0.126.147:65100"; // Endereço do Teste de Backend do SysImg - DESENVOLVIMENTO
                INSTANCE.allowedOrigins[1] = "http://192.168.0.20:5100"; // Intranet Casa do Cris
                INSTANCE.allowedOrigins[2] = "http://192.168.1.20:5100"; // Intranet Minha Casa
                INSTANCE.allowedOrigins[3] = "http://195.35.43.63:5100"; // Hostinger
                INSTANCE.allowedOrigins[4] = "http://127.0.0.1:5100"; // Localhost
                break;
        }
        INSTANCE.log.debN("Config.getAllowedOrigins()[0] ="+INSTANCE.getAllowedOrigins()[0]);
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

    public void setAllowedOrigins(String[] allowedOrigins){
        this.allowedOrigins = allowedOrigins;
    }

    public String[] getAllowedOrigins() {
        return this.allowedOrigins;
    }



/*   public void set#(String #){
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
