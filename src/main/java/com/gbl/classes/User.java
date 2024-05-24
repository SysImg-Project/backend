// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 28/04/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.classes;
import java.time.LocalDate;
import org.json.*;
import java.sql.*;
import java.util.Vector; // Para list()
import com.gbl.RetornoCrud;
//import com.gbl.classes.Pessoa;



public class User extends Pessoa {
    private String login;
    private String senha;

    public User() {
        super();
        log.logN("Entrou em: public User()");
        inicializacaoUser();
    }

    public User(int _id, String _nome, String _sobreNome, String _datanasIso, Sexo _sexo, Tipo _tipo, String _login, String _senha) {
        super(_id, _nome, _sobreNome, _datanasIso, _sexo, _tipo);
        log.logN("Entrou em: public User(int _id, String _nome, String _sobreNome, String _datanasIso, Sexo _sexo, Tipo _tipo, String _login, String _senha)");
        this.login = _login;
        this.senha = _senha;
        inicializacaoUser();
    }

    private void inicializacaoUser() { // Usado pelos construtores
        log.logN("Entrou em: private void inicializacaoUser()");
        // Faz as verificações para uso da DB:
        this.setTabelaNome("users");
        //public boolean existeATabelaX(String tabela) {

        log.dbN("db.existeATabelaX("+this.getTabelaNome()+") ="+db.existeATabelaX(this.getTabelaNome())); // Debug

        if (!db.existeATabelaX(this.getTabelaNome())) { // Se não tem a tabela cria-a !
            this.createTable();
        }
        //this.createTable(); // REMOVER !!!

    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getSenha() {
        return this.senha;
    }

    @Override
    public RetornoCrud createTable() {
        log.logN("Entrou em: public RetornoCrud createTable()");
        String comando = "";

        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
            /*comando = "CREATE TABLE usuarios ("
            "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, "
            "nome VARCHAR(100), "
            "sobrenome VARCHAR(100), "
            "datanas DATE, "
            "sexo TINYINT, "
            "login VARCHAR(100), "
            "senha VARCHAR(100) " // Não esquecer de retirar a vírgula final !
            ");"; // comando DDL -- retorna um int*/

            comando = "CREATE TABLE "+getTabelaNome()+" (";
            comando += "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, ";
            comando += "nome VARCHAR(100), ";
            comando += "sobrenome VARCHAR(100), ";
            comando += "datanas DATE, ";
            comando += "sexo TINYINT, ";
            comando += "tipo TINYINT, ";
            comando += "login VARCHAR(100), ";
            comando += "senha VARCHAR(100) "; // Não esquecer de retirar a vírgula final !
            comando += ");"; // comando DDL -- retorna um int
            log.dbN(comando);

            PreparedStatement updateStatement = conn.prepareStatement(comando);
            int ret = updateStatement.executeUpdate(); //Returns: either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
            log.dbN("ret ="+ret); // Esse retorno não indica um erro - Se tiver erro vai gerar uma SQLException

            RetornoCrud ret2 = new RetornoCrud(true, "", toJSON());
            return ret2;
        }
        catch (SQLException e) {
            log.erroN("LOCAL DO ERRO: public RetornoCrud User.createTable()");
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
            comando += "(nome, sobrenome, datanas, sexo, tipo, login, senha)";
            //comando += " VALUES (\"\", \"\", \"0000-01-01\", 0, 0, \"\", \"\");";
            comando += " VALUES (\""+getNome()+"\", \""+getSobreNome()+"\", \""+getDatanasIso()+"\", "+getSexoInt()+", "+getTipoInt()+", \""+getLogin()+"\", \""+getSenha()+"\");";
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
            log.erroN("LOCAL DO ERRO: public RetornoCrud User.create()");
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
                    this.setNome(result.getString("nome"));
                    this.setSobreNome(result.getString("sobrenome"));
                    //this.setDatanasIso(result.getDate("datanas").toString()); // Date getDate(String columnLabel)   throws SQLException --> Dá erros !
                    this.setDatanasIso(result.getString("datanas"));
                    this.setSexoInt(result.getInt("sexo")); // Não existe getTinyInt()
                    this.setTipoInt(result.getInt("tipo")); // Não existe getTinyInt()
                    this.setLogin(result.getString("login"));
                    this.setSenha(result.getString("senha"));
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
            log.erroN("LOCAL DO ERRO: public RetornoCrud User.read()");
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
            comando += "nome=\""+getNome()+"\", ";
            comando += "sobrenome=\""+getSobreNome()+"\", ";
            comando += "datanas=\""+getDatanasIso()+"\", ";
            comando += "sexo="+getSexoInt()+", ";
            comando += "tipo="+getTipoInt()+", ";
            comando += "login=\""+getLogin()+"\", ";
            comando += "senha=\""+getSenha()+"\""; // Remover a última vírgula
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
            log.erroN("LOCAL DO ERRO: public RetornoCrud User.update()");
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
            log.erroN("LOCAL DO ERRO: public RetornoCrud User.delete()");
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

            Vector<User> vec = new Vector<User>();
            if (result.next()) { // Significa que tem resultados
                do { // Recebe as colunas que estão dentro desta linha
                    this.setId(result.getInt("id"));
                    this.setNome(result.getString("nome"));
                    this.setSobreNome(result.getString("sobrenome"));

                    try {
                        log.dbN("result.getString(\"datanas\") ="+result.getString("datanas"));

                        this.setDatanasIso(result.getString("datanas"));

                        /*log.dbN("result.getDate(\"datanas\") ="+result.getDate("datanas"));
                        log.dbN("result.getDate(\"datanas\").toString() ="+result.getDate("datanas").toString());

                        this.setDatanasIso(result.getDate("datanas").toString()); // Date getDate(String columnLabel)   throws SQLException*/ // DELETAR !!!
                    }
                    catch (SQLException e) {
                        log.erroN("LOCAL DO ERRO: public RetornoCrud User.list() --> em: this.setDatanasIso(result.getDate(\"datanas\").toString()");
                        log.erroN(e.getSQLState()); // Código do Erro
                        log.erroN(e.toString()); // Info do Erro
                    }

                    finally {
                    this.setSexoInt(result.getInt("sexo")); // Não existe getTinyInt()
                    this.setTipoInt(result.getInt("tipo")); // Não existe getTinyInt()
                    this.setLogin(result.getString("login"));
                    this.setSenha(result.getString("senha"));
                    vec.add(this); // Adiciona o objeto atualizado no vetor
                    }
                }
                while (result.next()); // Passa para a próxima linha (row)
                // Retorna o resultado desta ação:
                JSONObject json = new JSONObject();
                JSONArray jsonArr = new JSONArray();
                //String json = "\"array\": [";

                // Popula o objeto json com os registros que satisfazem a condição:
                for (int i = 0; i < vec.size(); i++) {
                    //log.dbN("i ="+i); // Debug
                    jsonArr.put(vec.get(i).toJSON());
                    //json += vec.get(i).toJSON().toString();
                    //if (i == vec.size() - 1) json += " ]";
                    //else json += ", ";
                    //log.dbN("jsonArr.toString() ="+jsonArr.toString()); // Debug
                }
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
        obj += "\"id\": "+this.getId()+",";
        obj += "\"nome\":\""+this.getNome()+"\" ,";
        obj += "\"sobrenome\":\""+this.getSobreNome()+"\" ,";
        obj += "\"datanas\":\""+this.getDatanasIso()+"\" ,";
        obj += "\"sexo\": "+this.getSexoInt()+",";
        obj += "\"tipo\": "+this.getTipoInt()+",";
        obj += "\"login\":\""+this.getLogin()+"\" ,";
        obj += "\"senha\":\""+this.getSenha()+"\" ,";
        obj += "}";
        JSONObject json = new JSONObject(obj); // Pega a String recebida e cria um objeto JSON
        return json;
    }


    @Override
    public String toString() {
        return "User: ["+super.toString()+", login: "+this.login+", senha: "+this.senha+"]";
    }

}

