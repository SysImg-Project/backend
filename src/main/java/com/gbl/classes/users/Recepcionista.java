// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 02/06/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.classes.users;
import com.gbl.Crud;
import com.gbl.classes.User;
import com.gbl.classes.Sexo;
import com.gbl.classes.Tipo;
import com.gbl.classes.ExcecaoValorInvalido;
import com.gbl.RetornoCrud;
import java.time.LocalDate;
import org.json.*;
import java.sql.*;

public class Recepcionista extends Crud {
// Porquê a herança aqui não é: Recepcionista->User->Pessoa->Crud ?
// E sim é: Recepcionista->Crud sendo que User é uma 'agregada' (diamante branco) em Recepcionista ?
// - Para poder reaproveitar os métodos de acesso à DB já existente em User (sem ter que fazer o override em todos os métodos)
// - Observe que como Recepcionista vai ter uma tabela própria na DB ela precisa herdar os métodos de Crud para tal

    //private int id; // ---> Herdados de Crud.java
    //private String tabelaNome; // ---> Herdados de Crud.java
    private User user;

    public Recepcionista() {
        super();
        user = new User();
        inicializacaoRecepcionista();
    }

    public Recepcionista(int userId, String nome, String sobreNome, String datanasIso, Sexo sexo, Tipo tipo, String login, String senha) {
        user = new User(userId, nome, sobreNome, datanasIso, sexo, tipo, login, senha);
        inicializacaoRecepcionista();
    }

    private void inicializacaoRecepcionista() { // Usado pelos construtores
        log.logN("Entrou em: private void inicializacaoRecepcionista()");
        // Faz as verificações para uso da DB:
        this.setTabelaNome("recepcionistas");
        //public boolean existeATabelaX(String tabela) {

        log.dbN("db.existeATabelaX("+this.getTabelaNome()+") ="+db.existeATabelaX(this.getTabelaNome())); // Debug

        if (!db.existeATabelaX(this.getTabelaNome())) { // Se não tem a tabela cria-a !
            this.createTable();
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    @Override
    public RetornoCrud createTable() {
        log.logN("Entrou em: public RetornoCrud createTable()");
        String comando = "";

        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
            comando = "CREATE TABLE "+getTabelaNome()+" (";
            comando += "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, ";
            comando += "userId INT(6)"; // Não esquecer de retirar a vírgula final !
            comando += ");"; // comando DDL -- retorna um int
            log.dbN(comando);

            PreparedStatement updateStatement = conn.prepareStatement(comando);
            int ret = updateStatement.executeUpdate(); //Returns: either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
            log.dbN("ret ="+ret); // Esse retorno não indica um erro - Se tiver erro vai gerar uma SQLException

            RetornoCrud ret2 = new RetornoCrud(true, "", toJSON());
            return ret2;
        }
        catch (SQLException e) {
            log.erroN("LOCAL DO ERRO: public RetornoCrud Recepcionista.createTable()");
            log.erroN(e.getSQLState()); // Código do Erro
            log.erroN(e.toString()); // Info do Erro
            RetornoCrud ret3 = new RetornoCrud(false, e.getSQLState()+" - "+e.toString(), null);
            return ret3;
        }
    }

    //@Override
    //void deleteTable(); Já tem definido na classe base Crud

    @Override
    public RetornoCrud create() { // 'int' Pq retorna o número do índice inserida na tabela
        log.logN("Entrou em: public RetornoCrud create()");
        String comando = "";

        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
        //comando = "INSERT INTO usuarios (nome, sobrenome) VALUES ('Paulo', 'Griebler');"; // Não insere no campo 'id' pois ele é auto incrementeado ! // comando INSERT -- retorna um int
            comando = "INSERT INTO "+getTabelaNome()+" "; // comando DDL -- retorna um int
            comando += "(userId)";
            //comando += " VALUES (\"\", \"\", \"0000-01-01\", 0, 0, \"\", \"\");";
            comando += " VALUES ("+user.getId()+");";
            log.dbN(comando);

            PreparedStatement updateStatement = conn.prepareStatement(comando);
            int ret = updateStatement.executeUpdate(); //Returns: either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
            log.dbN("ret ="+ret); // Esse retorno não indica um erro - Se tiver erro vai gerar uma SQLException


            // Se chegou aqui teve sucesso na inserção do registro
            comando = "SELECT LAST_INSERT_ID();";
            int lastInsertId = -1;
            log.dbN(comando);
            PreparedStatement selectStatement = conn.prepareStatement(comando);
            ResultSet result = selectStatement.executeQuery(); //Returns: a ResultSet object that contains the data produced by the query; never null

            if (result.next()) { // Significa que tem resultados
                do { // Recebe as colunas que estão dentro desta linha
                    lastInsertId = result.getInt("LAST_INSERT_ID()");
                }
                while (result.next()); // Passa para a próxima linha (row)
                // Retorna o resultado desta ação:
                this.setId(lastInsertId); // Atualiza o id do registro criado no modelo User para o no retorno do JSON indicar o id do mesmo
                RetornoCrud ret3 = new RetornoCrud(true, "", toJSON());
                ret3.setInteiro(lastInsertId);
                return ret3;
            }
            else { // Significa que NÃO teve resultados
                RetornoCrud ret4 = new RetornoCrud(false, "Houve um erro na operação 'create' -- não foi possível determinar 'lastInsertId'", null);
                return ret4;
            }

        }
        catch (SQLException e) {
            log.erroN("LOCAL DO ERRO: public RetornoCrud Recepcionista.create()");
            log.erroN(e.getSQLState()); // Código do Erro
            log.erroN(e.toString()); // Info do Erro
            RetornoCrud ret = new RetornoCrud(false, e.getSQLState(), null);
            return ret;
        }
    }

    @Override
    public RetornoCrud read(int id) {
        log.logN("Entrou em: public RetornoCrud read(int "+id+");");
        String comando = "";

        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
            comando = "SELECT * FROM "+getTabelaNome()+" WHERE id="+id+";";

            log.dbN(comando);
            PreparedStatement selectStatement = conn.prepareStatement(comando);
            ResultSet result = selectStatement.executeQuery(); //Returns: a ResultSet object that contains the data produced by the query; never null

            if (result.next()) { // Significa que tem resultados
                do { // Recebe as colunas que estão dentro desta linha
                    this.setId(result.getInt("id"));
                    this.user.read(result.getInt("userId")); // Precisa chamar o método 'read' na agregação tb!
                }
                while (result.next()); // Passa para a próxima linha (row)
                // Retorna o resultado desta ação:
                RetornoCrud ret = new RetornoCrud(true, "", toJSON());
                return ret;
            }
            else { // Significa que NÃO teve resultados
                // Retorna o resultado desta ação:
                RetornoCrud ret = new RetornoCrud(false, "Não existe um registro para a tabela '"+getTabelaNome()+"' com a 'id' = "+id+"!", null); // Não pode mandar um JSON com o modelo de User aqui pq gera erros em LocalDate e tb pq não existe um registro com a id informada
                return ret;
            }
        }
        catch (SQLException e) {
            log.erroN("LOCAL DO ERRO: public RetornoCrud Recepcionista.read()");
            log.erroN(e.getSQLState()); // Código do Erro
            log.erroN(e.toString()); // Info do Erro
            RetornoCrud ret = new RetornoCrud(false, e.getSQLState()+" - "+e.toString(), null);
            return ret;
        }

    }

    @Override
    public RetornoCrud update(int id) {
        log.logN("Entrou em: public RetornoCrud update(int "+id+");");
        String comando = "";

        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
            comando = "UPDATE "+getTabelaNome()+" SET ";
            comando += "userId="+this.user.getId()+" "; // Remover a última vírgula
            comando += "WHERE id="+id+";";

            log.dbN(comando);
            //PreparedStatement selectStatement = conn.prepareStatement(comando);
            //ResultSet result = selectStatement.executeQuery();
            PreparedStatement updateStatement = conn.prepareStatement(comando);
            int ret = updateStatement.executeUpdate(); //Returns: either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
            log.dbN("ret ="+ret); // Esse retorno não indica um erro - Se tiver erro vai gerar uma SQLException

            // Avalia o resultado da ação:
            RetornoCrud ret2 = new RetornoCrud(true, "", toJSON());
            return ret2;
        }
        catch (SQLException e) {
            log.erroN("LOCAL DO ERRO: public RetornoCrud Medico.update()");
            log.erroN(e.getSQLState()); // Código do Erro
            log.erroN(e.toString()); // Info do Erro
            RetornoCrud ret = new RetornoCrud(false, e.getSQLState()+" - "+e.toString(), null);
            return ret;
        }
    }

    @Override
    public RetornoCrud delete(int id) {
        log.logN("Entrou em: public RetornoCrud delete(int "+id+");");
        //DELETE FROM table_name WHERE some_column = some_value
        String comando = "";

        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
            comando = "DELETE FROM "+getTabelaNome()+" "; // comando DDL -- retorna um int
            comando += "WHERE id = "+id+";";
            log.dbN(comando);

            PreparedStatement updateStatement = conn.prepareStatement(comando);
            int ret = updateStatement.executeUpdate(); //Returns: either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
            log.dbN("ret ="+ret); // Esse retorno não indica um erro - Se tiver erro vai gerar uma SQLException

            // Avalia o resultado da ação:
            RetornoCrud ret2 = new RetornoCrud(true, "", null);
            return ret2;
        }
        catch (SQLException e) {
            log.erroN("LOCAL DO ERRO: public RetornoCrud Recepcionista.delete()");
            log.erroN(e.getSQLState()); // Código do Erro
            log.erroN(e.toString()); // Info do Erro
            RetornoCrud ret = new RetornoCrud(false, e.getSQLState()+" - "+e.toString(), null);
            return ret;
        }
    }

    @Override
    public RetornoCrud list(String condicao) {
        log.logN("Entrou em: public RetornoCrud list(String "+condicao+");");
        String comando = "";

        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
            //comando = "SELECT * FROM "+getTabelaNome()+" WHERE "+condicao+";";
            comando = "SELECT * FROM "+getTabelaNome()+" "+condicao+";"; // WHERE vem dentro da String 'condicao'
            log.dbN(comando);
            PreparedStatement selectStatement = conn.prepareStatement(comando);
            ResultSet result = selectStatement.executeQuery(); //Returns: a ResultSet object that contains the data produced by the query; never null

            JSONObject json = new JSONObject();
            JSONArray jsonArr = new JSONArray();
            if (result.next()) { // Significa que tem resultados
                do { // Recebe as colunas que estão dentro desta linha
                    this.setId(result.getInt("id"));
                    this.user.setId(result.getInt("userId"));
                    jsonArr.put(toJSON()); // Pode fazer direto isso !
                }
                while (result.next()); // Passa para a próxima linha (row)
                // Retorna o resultado desta ação:
                //String json = "\"array\": [";

                //json.put("array", jsonArr.toString()); // Põe o Array de JSONs dentro do Obj. json
                json.put("array", jsonArr);

                log.dbN("json.toString() ="+json.toString()); // Debug

                //RetornoCrud ret = new RetornoCrud(true, "", toJSON());
                RetornoCrud ret = new RetornoCrud(true, "", json);
                return ret;
            }
            else { // Significa que NÃO teve resultados -- O QUE PODE SER UM RESULTADO CORRETO !
                // Retorna o resultado desta ação:
                RetornoCrud ret = new RetornoCrud(true, "Não existem registros na tabela '"+getTabelaNome()+"' que atendem às condições especificadas: "+condicao+" !", null); // Não pode mandar um JSON com o modelo de User aqui pq não existem registros com as condicoes informadas
                return ret;
            }
        }
        catch (SQLException e) {
            log.erroN("LOCAL DO ERRO: public RetornoCrud User.list()");
            log.erroN(e.getSQLState()); // Código do Erro
            log.erroN(e.toString()); // Info do Erro
            RetornoCrud ret = new RetornoCrud(false, e.getSQLState()+" - "+e.toString(), null);
            return ret;
        }

    }


    @Override
    // Cria uma PARTE de um objeto JSON (faltam os '{}' que envolvem 'data')
    public JSONObject toJSON() {
        //{"object":"user","action":"create","data":{"id":null,"nome":"","sobrenome":"","datanas":"","sexo":"0","login":"","senha":""}}

        //String obj = "data: {"
        String obj = "{"; // Sem a id 'data', isso será colocado na resposta
        obj += "\"id\":"+this.getId()+",";
        obj += "\"userId\":"+this.user.getId(); // Não coloca "," no último ítem
        obj += "}";
        JSONObject json = new JSONObject(obj); // Pega a String recebida e cria um objeto JSON
        return json;
    }


    @Override
    public String toString() {
        return "Recepcionista: [id: "+this.getId()+", "+this.user.toString()+"]";
    }

}



