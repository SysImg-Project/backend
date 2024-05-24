// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 11/05/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl;
import java.util.Map;

import com.gbl.Log; // Para obter 'log'
import com.gbl.DB; // Para obter 'conn'
import org.json.*; // Para JSON
import com.gbl.RetornoCrud;
import java.sql.*;


// Classe que deve ser herdada por todas as classes que forem representar modelos de objetos (Ex.: Pessoa, Paciente, Usuario) que forem precisar do armazenamento dos mesmos na DB
public class Crud implements ICrud {
    //private Log log; // Instância do objeto Log -- para logs
    //private DB db; // Instância do objeto DB -- para uso da database
    protected Log log; // Instância do objeto Log -- para logs
    protected DB db; // Instância do objeto DB -- para uso da database

    private int id;
    private String tabelaNome;
    //private Map<String, String> campos;

    public Crud() { // Criei aqui pq o compilador estava criando automaticamente e sem as inicializações necessárias
        //log.logN("Entrou em: public Crud()"); --> Não tem o objeto 'log' definido aqui ainda
        System.out.println("Entrou em: public Crud()"); // Debug
        inicializacaoCrud(0);
    }

    public Crud(int _id) {
        //log.logN("Entrou em: public Crud(int _id)"); --> Não tem o objeto 'log' definido aqui ainda
        System.out.println("Entrou em: public Crud(int "+_id+")"); // Debug
        inicializacaoCrud(_id);
    }

    private void inicializacaoCrud(int _id) {
        //log.logN("Entrou em: private void inicializacaoCrud(int _id)"); --> Não tem o objeto 'log' definido aqui ainda
        System.out.println("Entrou em: private void inicializacaoCrud(int "+_id+")"); // Debug
        this.log = Log.getInstance(); // Obtêm uma instância do objeto Log -- para logs
        this.db = DB.getInstance(); // Obtêm uma instância do objeto DB -- para uso da database
        this.id = _id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setTabelaNome(String tabelaNome) {
        this.tabelaNome = tabelaNome;
    }

    public String getTabelaNome() { // Mostra o nome da tabela que está representando este modelo na DB
        return this.tabelaNome;
    }

    //public Map<String, String> getCampos(); // Mostra em um mapa o nome dos campos e suas características

    public RetornoCrud createTable() {
        //public RetornoCrud(boolean sucesso, String msg, JSONObject json) {
        RetornoCrud ret = new RetornoCrud(false, "", null);
        return ret;
    }

    public RetornoCrud deleteTable() { // Implementação padrão ! -- A maioria das classes que herdarem este método não precisam fazer override dele
        log.logN("Entrou em: public RetornoCrud deleteTable();");
        String comando = "";

        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
            comando = "DROP table "+tabelaNome+";"; // comando DDL -- retorna um int
            log.dbN(comando);

            PreparedStatement updateStatement = conn.prepareStatement(comando);
            int ret = updateStatement.executeUpdate(); //Returns: either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
            log.dbN("ret ="+ret); // Esse retorno não indica um erro - Se tiver erro vai gerar uma SQLException

            RetornoCrud ret2 = new RetornoCrud(true, "", null);
            return ret2;
        }
        catch (SQLException e) {
            log.erroN("LOCAL DO ERRO: public RetornoCrud Crud.deleteTable()");
            log.erroN(e.toString()); // log de erro (console e/ou arquivo)
            RetornoCrud ret = new RetornoCrud(false, e.toString(), null);
            return ret;
        }

    }

    public RetornoCrud create() { // 'int' Pq retorna o número do índice inserida na tabela
        RetornoCrud ret = new RetornoCrud(false, "", null);
        return ret;
    }

    public RetornoCrud read(int id) {
        RetornoCrud ret = new RetornoCrud(false, "", null);
        return ret;
    }

    public RetornoCrud update(int id) {
        RetornoCrud ret = new RetornoCrud(false, "", null);
        return ret;
    }


    public RetornoCrud delete(int id) {
        RetornoCrud ret = new RetornoCrud(false, "", null);
        return ret;
    }

    public RetornoCrud deleteAll() { // Implementação padrão ! -- A maioria das classes que herdarem este método não precisam fazer override dele
        log.logN("Entrou em: public RetornoCrud deleteAll();");
        String comando = "";

        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
            comando = "DELETE FROM "+tabelaNome+";"; // comando DDL -- retorna um int
            log.dbN(comando);

            PreparedStatement updateStatement = conn.prepareStatement(comando);
            int ret = updateStatement.executeUpdate(); //Returns: either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
            log.dbN("ret ="+ret); // Esse retorno não indica um erro - Se tiver erro vai gerar uma SQLException

            RetornoCrud ret2 = new RetornoCrud(true, "", null);
            return ret2;
        }
        catch (SQLException e) {
            log.erroN("LOCAL DO ERRO: public RetornoCrud Crud.deleteAll()");
            log.erroN(e.toString()); // log de erro (console e/ou arquivo)
            RetornoCrud ret = new RetornoCrud(false, e.toString(), null);
            return ret;
        }
    }

    public RetornoCrud list(String condicao) {
        RetornoCrud ret = new RetornoCrud(false, "", null);
        return ret;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject(); // Pega a String recebida e cria um objeto JSON
        return json;
    }

}
