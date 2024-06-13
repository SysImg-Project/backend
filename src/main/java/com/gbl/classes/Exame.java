// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 02/06/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.classes;
import com.gbl.Crud;
import com.gbl.classes.ExameImagemTipo;
import com.gbl.classes.RegiaoAnatomica;
import com.gbl.RetornoCrud;
//import com.gbl.classes.DCM;
import com.gbl.classes.ExcecaoValorInvalido;

import org.json.*;
import java.sql.*;

public class Exame extends Crud {
    private int pacienteId;
    //private Paciente paciente;
    private ExameImagemTipo tipo;
    private RegiaoAnatomica regiao;
    //private DCM[] dcm;

    public Exame() {
        super(0);
        this.pacienteId = 0; // Continua a execução com valores seguros
        //this.paciente = new Paciente(); // Construtor vazio com valores seguros
        this.tipo = new ExameImagemTipo(); // Construtor vazio com valores seguros
        this.regiao = new RegiaoAnatomica(); // Construtor vazio com valores seguros
        inicializacaoExame();

        /*this.pacienteId = 0;
        try {
            ExameImagemTipo eb = new ExameImagemTipo("IN"); // {"IN", "INDEFINIDO"}, // 0
            this.tipo = eb;
        }
        catch (ExcecaoValorInvalido e) {
            log.erroN(e.toString());
            log.erroN("Não foi possível inicializar a classe ExameImagemTipo com o valor 'IN' (estado indefinido).");
            // Aqui deve parar o programa pois é um erro muito grave !
            log.erroN("PARADA DO PROGRAMA DEVIDO AOS ERROS ACIMA !!!")
            System.exit(1);
        }*/ // DELETAR -- acho que não preciso de tudo isso -- só usar construtores vazios
    }

    public Exame(int id, int pacienteId, ExameImagemTipo tipo, RegiaoAnatomica regiao) {
        super(id);
        this.pacienteId = pacienteId;
        this.tipo = tipo;
        this.regiao = regiao;
        inicializacaoExame();
    }

    public Exame(int id, int pacienteId, String tipo, String regiao) {
        super(id);
        this.pacienteId = pacienteId;
        try {
            ExameImagemTipo eb = new ExameImagemTipo(tipo);
            this.tipo = eb;
        }
        catch (ExcecaoValorInvalido e) {
            log.erroN(e.toString());
            this.pacienteId = 0; // Continua a execução com valores seguros
            this.tipo = new ExameImagemTipo(); // Construtor vazio com valores seguros
        }

        try {
            RegiaoAnatomica ra = new RegiaoAnatomica(regiao);
            this.regiao = ra;
        }
        catch (ExcecaoValorInvalido e) {
            log.erroN(e.toString());
            this.pacienteId = 0; // Continua a execução com valores seguros
            this.regiao = new RegiaoAnatomica(); // Construtor vazio com valores seguros
        }
        inicializacaoExame();

    }

    private void inicializacaoExame() { // Usado pelos construtores
        log.logN("Entrou em: private void inicializacaoExame()");
        // Faz as verificações para uso da DB:
        this.setTabelaNome("exames");
        //public boolean existeATabelaX(String tabela) {

        log.dbN("db.existeATabelaX("+this.getTabelaNome()+") ="+db.existeATabelaX(this.getTabelaNome())); // Debug

        if (!db.existeATabelaX(this.getTabelaNome())) { // Se não tem a tabela cria-a !
            this.createTable();
        }
    }

    /*public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }*/ // Deletar !!!

    public void setPacienteId(int pacienteId) {
        if ((pacienteId < 0) || (pacienteId > 1000000)) {
        // Fora da faixa --> throw
            throw new ExcecaoValorInvalido("O inteiro informado para indicar um número de registro de um paciente não está dentro da faixa de valores válidos que vai de 0 à 1000000 (1 milhão).\nint informado ="+pacienteId+".\n");
        }
        else {
            this.pacienteId = pacienteId;
        }
    }

    public int getPacienteId() {
        return this.pacienteId;
    }


    // Tipo:
    public void setTipo(ExameImagemTipo tipo){
        this.tipo = tipo;
    }

    // Aceita sigla ou nome por extenso do estado
    public void setTipoStr(String tipo) {
        try {
            ExameImagemTipo eb = new ExameImagemTipo(tipo);
            this.tipo = eb;
        }
        catch (ExcecaoValorInvalido e) {
            log.erroN(e.toString());
            this.tipo = new ExameImagemTipo(); // Construtor vazio com valores seguros
        }
    }

    public void setTipoInt(int tipoInt) {
        try {
            ExameImagemTipo eb = new ExameImagemTipo();
            //public void setCodigoEstado(int codigoEstado) throws ExcecaoValorInvalido {
            eb.setCodigoExame(tipoInt);
            this.tipo = eb;
        }
        catch (ExcecaoValorInvalido e) {
            log.erroN(e.toString());
            this.tipo = new ExameImagemTipo(); // Construtor vazio com valores seguros
        }
    }

    public ExameImagemTipo getTipo() {
        return tipo;
    }

    public String getTipoSigla() {
        return tipo.getCodigoExameSigla();
    }

    public int getTipoInt() {
        return tipo.getCodigoExame();
    }

    // Regiao:
    public void setRegiao(RegiaoAnatomica regiao){
        this.regiao = regiao;
    }

    // Aceita sigla ou nome por extenso do estado
    public void setRegiaoStr(String regiao) {
        try {
            RegiaoAnatomica ra = new RegiaoAnatomica(regiao);
            this.regiao = ra;
        }
        catch (ExcecaoValorInvalido e) {
            log.erroN(e.toString());
            this.regiao = new RegiaoAnatomica(); // Construtor vazio com valores seguros
        }
    }

    public void setRegiaoInt(int regiaoInt) {
        try {
            RegiaoAnatomica ra = new RegiaoAnatomica();
            //public void setCodigoEstado(int codigoEstado) throws ExcecaoValorInvalido {
            ra.setCodigoRegiao(regiaoInt);
            this.regiao = ra;
        }
        catch (ExcecaoValorInvalido e) {
            log.erroN(e.toString());
            this.regiao = new RegiaoAnatomica(); // Construtor vazio com valores seguros
        }
    }

    public RegiaoAnatomica getRegiao() {
        return regiao;
    }

    public String getRegiaoSigla() {
        return regiao.getCodigoRegiaoSigla();
    }

    public int getRegiaoInt() {
        return regiao.getCodigoRegiao();

    }





    @Override
    public RetornoCrud createTable() {
        log.logN("Entrou em: public RetornoCrud createTable()");
        String comando = "";

        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
            comando = "CREATE TABLE "+getTabelaNome()+" (";
            comando += "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, ";
            comando += "tipo TINYINT, ";
            comando += "regiao TINYINT, ";
            comando += "pacienteId INT(6)"; // Não esquecer de retirar a vírgula final !
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
            comando += "(tipo, regiao, pacienteId)";
            //comando += " VALUES (\"\", \"\", \"0000-01-01\", 0, 0, \"\", \"\");";
            comando += " VALUES ("+getTipoInt()+", "+getRegiaoInt()+", "+pacienteId+");";
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
            log.erroN("LOCAL DO ERRO: public RetornoCrud Exame.create()");
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
                    this.setPacienteId(result.getInt("pacienteId")); // Precisa chamar o método 'read' na agregação tb!
                    this.setTipoInt(result.getInt("tipo"));
                    this.setRegiaoInt(result.getInt("regiao"));
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
            log.erroN("LOCAL DO ERRO: public RetornoCrud Exame.read()");
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
            comando += "pacienteId="+getPacienteId()+", ";
            comando += "tipo="+getTipoInt()+", ";
            comando += "regiao="+getRegiaoInt()+" "; // Remover a última vírgula
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
            log.erroN("LOCAL DO ERRO: public RetornoCrud Exame.update()");
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
                    this.setPacienteId(result.getInt("pacienteId"));
                    this.setTipoInt(result.getInt("tipo"));
                    this.setRegiaoInt(result.getInt("regiao"));
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
        obj += "\"pacienteId\":"+this.getPacienteId()+"\" ,";
        obj += "\"tipo\":\""+this.getTipoSigla()+"\" ,";
        obj += "\"regiao\":\""+this.getRegiaoSigla(); // Não coloca "," no último ítem
        obj += "}";
        JSONObject json = new JSONObject(obj); // Pega a String recebida e cria um objeto JSON
        return json;
    }

    @Override
    public String toString() {
        return "Exame [pacienteId: "+this.pacienteId+", tipo: "+this.tipo.getCodigoExameSigla()+", regiao: "+this.regiao.getCodigoRegiaoSigla()+"]";
    }

}

