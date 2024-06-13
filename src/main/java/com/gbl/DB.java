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
import java.sql.*; // Para a classe Connection


/*
Classe responsável por gerenciar chaves de configuração do aplicativo
Usa o padrão de projeto Singleton
*/

public final class DB implements IDB {
    private static DB INSTANCE; // Todo o Singleton deve ter um campo estático que contêm a única instância

    private Log log; // Instância do objeto Log -- para logs

    private String dbHost; // Número de host (ou nome ?) para acesso à DB MySQL usando JDBC
    private String dbPort; // Porta "
    private String dbLogin; // Login "
    private String dbSenha; // Senha "
    private String dbNome; // Nome da database -- varia conforme o 'modo' na classe Config -- existe um nome de database para os modos '_des', '_tst' e producao '(nome da database sem nenhum sufixo)'

    //Del private Connection conn; // Objeto Connection para permitir as ações sobre a DB

    public String conexaoStr; //= "jdbc:mysql://"+this.dbHost+"/"+this.dbNome+"?serverTimezone=UTC";
    public String conexaoLogin; // = this.dbLogin;
    public String conexaoSenha; // = this.dbSenha;

    private DB() { // Todo o Singleton deve ter um construtor privado -- só acessível através do método getInstance()
        //log.logN("Entrou em: "); --> Não tem o objeto 'log' definido aqui ainda
        System.out.println("Entrou em: private DB()"); // Debug
        this.log = Log.getInstance(); // Obtêm uma instância do objeto Log -- para logs
        //determinaMododeTrabalho(); // Não pode ser chamado aqui porque a INSTANCE ainda é null !
    }

    public static DB getInstance() { // Todo o Singleton deve conter um método fábrica estático que permita obter a instância
        //log.logN("Entrou em: "); --> Não tem o objeto 'log' definido aqui ainda
        System.out.println("Entrou em: public static DB getInstance()"); // Debug
        if (INSTANCE == null) {
            INSTANCE = new DB();
        }
        determinaMododeTrabalho(); // Chama aqui porque a INSTANCE já deve estar definida (não nula !)
        return INSTANCE;
    }

    private static void determinaMododeTrabalho() {
        // Verifica o tipo de modo de execução conforme o que estiver no atributo 'modo' da classe Config:
        switch(Config.getInstance().toStringModo()) {
            case "PRODUCAO":
                INSTANCE.setarPropriedadesModoProducao(); // INSTANCE Pq ? --> non-static method setarPropriedadesModoProducao() cannot be referenced from a static context
                break;
            case "DESENVOLVIMENTO":
                INSTANCE.setarPropriedadesModoDesenvolvimento();
                break;
            case "TESTE":
                INSTANCE.setarPropriedadesModoTeste();
                break;
            case "INDEFINIDO":
            default:
                INSTANCE.setarPropriedadesModoDesenvolvimento();
                break;
        }
        INSTANCE.conexaoStr = "jdbc:mysql://"+INSTANCE.dbHost+"/"+INSTANCE.dbNome+"?serverTimezone=UTC";
        INSTANCE.conexaoLogin = INSTANCE.dbLogin;
        INSTANCE.conexaoSenha = INSTANCE.dbSenha;
        INSTANCE.log.db("LOCAL : public static DB getInstance() ==> ");
        INSTANCE.log.dbN("INSTANCE.conexaoStr ="+INSTANCE.conexaoStr);
        INSTANCE.log.dbN("INSTANCE.conexaoLogin ="+INSTANCE.conexaoLogin);
        INSTANCE.log.dbN("INSTANCE.conexaoSenha ="+INSTANCE.conexaoSenha);
    }

    private void setarPropriedadesModoDesenvolvimento() {
        log.logN("Entrou em: private void setarPropriedadesModoDesenvolvimento()");
        // Conforme foi criado no MySQL do astrolabium para o modo desenvolvimento:
        //CREATE DATABASE sysimg_des;
        //CREATE USER 'sysimg_des'@'localhost' IDENTIFIED BY 'D8yx3Rk2';
        //this.dbHost = "192.168.1.20"; // java.sql.SQLException: null,  message from server: "Host '192.168.1.20' is not allowed to connect to this MariaDB server"
        this.dbHost = "127.0.0.1";
        this.dbPort = "3306";
        this.dbLogin = "sysimg_des";
        this.dbSenha = "D8yx3Rk2";
        this.dbNome = "sysimg_des";
    }

    private void setarPropriedadesModoTeste() {
        log.logN("Entrou em: private void setarPropriedadesModoTeste()");
        // Conforme foi criado no MySQL do astrolabium para o modo desenvolvimento:
        //CREATE DATABASE sysimg_tst;
        //CREATE USER 'sysimg_sys'@'localhost' IDENTIFIED BY '3Nwqi89L';
        this.dbHost = "127.0.0.1";
        this.dbPort = "3306";
        this.dbLogin = "sysimg_tst";
        this.dbSenha = "3Nwqi89L";
        this.dbNome = "sysimg_tst";
    }

    private void setarPropriedadesModoProducao() {
        log.logN("Entrou em: private void setarPropriedadesModoProducao()");
        // Conforme foi criado no MySQL do astrolabium para o modo producao
        //CREATE DATABASE sysimg;
        //CREATE USER 'sysimg'@'localhost' IDENTIFIED BY 'n2Qi4RWl';
        this.dbHost = "127.0.0.1";
        this.dbPort = "3306";
        this.dbLogin = "sysimg";
        this.dbSenha = "n2Qi4RWl";
        this.dbNome = "sysimg";
    }


    /*public void conectar() {
        log.logN("Entrou em: public void conectar()");
        //try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/sysimg_des?serverTimezone=UTC","sysimg_des", "D8yx3Rk2")) { // No fim a DB e o servidor vão estar localizados no Astrolabium o que torna tudo acesso local

        //try { // Este try não funciona !!! --> tem que ser o try() {
        //String conexaoStr = "jdbc:mysql://"+this.dbHost+"/"+this.dbNome+"?serverTimezone=UTC"; //the try-with-resources resource must either be a variable declaration or an expression denoting a reference to a final or effectively final variable
        this.conexaoStr = "jdbc:mysql://"+this.dbHost+"/"+this.dbNome+"?serverTimezone=UTC";
        this.conexaoLogin = this.dbLogin;
        this.conexaoSenha = this.dbSenha;
        log.db("LOCAL : public void DB.conectar() ==> ");
        log.dbN("this.conexaoStr ="+this.conexaoStr);
        log.dbN("this.conexaoLogin ="+this.conexaoLogin);
        log.dbN("this.conexaoSenha ="+this.conexaoSenha);


        try(Connection conn = DriverManager.getConnection(this.conexaoStr, this.conexaoLogin, this.conexaoSenha)) {

        //try (this.conn) {
        //try (Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost/sysimg_des?serverTimezone=UTC","sysimg_des", "D8yx3Rk2")) { //USANDO ISTO PQ É O QUE FUNCIONA POR ENQUANTO -- REVER !!!
        //try (Connection this.conn = DriverManager.getConnection("jdbc:mysql://localhost/sysimg_des?serverTimezone=UTC","sysimg_des", "D8yx3Rk2")) {
        //this.conn = conn2;

        // Quando sai daqui a conexão fecha automático !!! ==> Isso é o que significa try-with-resources -- quando sai do try a conexão é automaticamente fechada...
        //Gbl-ERRO: java.sql.SQLNonTransientConnectionException: No operations allowed after connection closed.
        }
        catch(SQLException e) {
            log.erro("LOCAL DO ERRO: public void DB.conectar() ==> ");
            log.erroN(e.toString()); // log erro (console e/ou arquivo)
        }
    }*/ // Del

    /*public Connection getConn() {
        log.logN("Entrou em: ");
        return this.conn;
    }*/ // Del

    //public existeADatabase() { // TODO -- Precisa ?

    //}

    public boolean existeATabelaX(String tabela) {
        log.logN("Entrou em: public boolean existeATabelaX(String "+tabela+")");
        /*MariaDB [sysimg_des]> SHOW TABLES;
        +----------------------+
        | Tables_in_sysimg_des |
        +----------------------+
        | usuarios             |
        +----------------------+*/
        String comando = "SHOW TABLES";

        /*final String conexaoStr = "jdbc:mysql://"+this.dbHost+"/"+this.dbNome+"?serverTimezone=UTC";
        final String conexaoLogin = this.dbLogin;
        final String conexaoSenha = this.dbSenha;*/
        //try(Connection conn = DriverManager.getConnection(conexaoStr, conexaoLogin, conexaoSenha)) {

        log.dbN("this.conexaoStr ="+this.conexaoStr);
        log.dbN("this.conexaoLogin ="+this.conexaoLogin);
        log.dbN("this.conexaoSenha ="+this.conexaoSenha);

        try(Connection conn = DriverManager.getConnection(this.conexaoStr, this.conexaoLogin, this.conexaoSenha)) {

            /*log.dbN("this.conexaoStr ="+this.conexaoStr);
            log.dbN("this.conexaoLogin ="+this.conexaoLogin);
            log.dbN("this.conexaoSenha ="+this.conexaoSenha);
            log.dbN("conn.isValid(0) ="+conn.isValid(0));*/

            PreparedStatement selectStatement = conn.prepareStatement(comando);
            ResultSet result = selectStatement.executeQuery();
            log.dbN(comando);
            log.dbN("result ="+result); // Não sei se isso dá certo

            //boolean retorno = false;
            while (result.next()) { // will traverse through all rows

                log.dbN("result.getString(\"Tables_in_"+this.dbNome+"\") ="+result.getString("Tables_in_"+this.dbNome)); // Debug

                if (result.getString("Tables_in_"+this.dbNome).equals(tabela)) { // Se achou o nome da tabela na lista retorna true !
                return true;
                }
            }
            return false;
        }
        catch (SQLException e) {
            log.erroN("LOCAL DO ERRO: public boolean DB.existeATabelaX()");
            log.erroN(e.getSQLState()); // Código do Erro
            log.erroN(e.toString()); // Info do Erro
            return false;
        }
    }

    @Override
    public void criarTabela(String tabela, Map<String, String> campos) { // Cria a tabela solicitada no parâmetro com os campos da lista

    }

    @Override
    public void deletarTabela(String tabela) { // Deleta a tabela indicada

    }

    @Override
    public int inserirNovoRegistro(String tabela, Map<String, String> valores) { // Insere um NOVO registro na tabela indicada com os valores dos campos solicitados e retorna o valor do índice criado

    return 0; // TODO
    }

    @Override
    public String[][] obterRegistros(String tabela, String[] campos, String condicao) { // Obtém registros da tabela solicitada, recebendo o resultado em uma matriz com os campos solicitados seguindo a condicao estabelecida

    //return new String[1][1]{""{""}}; // TODO
    String[][] ret = new String[1][1];
    return ret; // TODO
    }

}
