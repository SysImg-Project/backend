// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 25/05/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.app.endpoint;
import org.json.*; // Para JSON
import com.gbl.Log;
import com.gbl.RetornoCrud;
import com.gbl.classes.User;
import java.time.LocalDate;
import com.gbl.classes.Sexo;
import com.gbl.classes.users.Recepcionista;
import com.gbl.classes.users.Paciente;
import com.gbl.classes.Exame;
import com.gbl.classes.DICOM;
import com.gbl.classes.users.profissionalSaude.Medico;

// Criada porque a classe Crud.java ficou muito grande !
//public class CrudAux {
public class CrudAux<T> { // Precisa informar na classe que algum método pode usar genéricos ! Usando genéricos para reduzir o número de métodos a escrever -- escrevo um método genérico que serve para todas as classes (nem sempre é possível...)
    private Log log; // Instância do objeto Log -- para logs

    public CrudAux() {
        this.log = Log.getInstance(); // Obtêm uma instância do objeto Log -- para logs
    }

    // User:
    public String createUser(JSONObject dataObj) {
        JSONObject jsonEnviado = new JSONObject(); // A resposta !

        log.debN("Antes de: User user = new User();");
        //public static LocalDate parse(CharSequence text)
        //LocalDate datanas = LocalDate.parse("0000-01-01"); // "0000-00-00" ==> não dá --> java.time.format.DateTimeParseException: Text '0000-00-00' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 0

        /*User user = new User(0, dataObj.getString("nome"), dataObj.getString("sobrenome"), LocalDate.parse(dataObj.getString("datanas")), dataObj.getInt("sexo"), dataObj.getInt("tipo"), dataObj.getString("login"), dataObj.getString("senha"));*/
        // Mais fácil usar o construtor vazio e depois ir preenchendo os campos para poder fazer as conversões adequadamente !!!
        User user = new User();
        user.setId(0);
        user.setNome(dataObj.getString("nome"));
        user.setSobreNome(dataObj.getString("sobrenome"));

        log.debN("dataObj.getString(\"datanas\") ="+ dataObj.getString("datanas")); // Debug

        user.setDatanasIso(dataObj.getString("datanas"));

        log.debN("user.getDatanasIso() ="+user.getDatanasIso()); // Debug

        user.setSexoInt(dataObj.getInt("sexo"));
        user.setTipoInt(dataObj.getInt("tipo"));
        user.setLogin(dataObj.getString("login"));
        user.setSenha(dataObj.getString("senha"));

        log.debN("Antes de: RetornoCrud ret = user.create();");
        RetornoCrud ret = user.create();

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu criar o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível criar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }

        return jsonEnviado.toString();
    }

    public String readUser(JSONObject dataObj) {
        log.debN("Entrou em: public String readUser(JSONObject dataObj)");
        GenericoCrudAux<User> gen = new GenericoCrudAux<User>(new User());
        return gen.readGen(dataObj);
    }

     public String updateUser(JSONObject dataObj) {
        JSONObject jsonEnviado = new JSONObject(); // A resposta !

        log.debN("Antes de: User user = new User();");
        // Mais fácil usar o construtor vazio e depois ir preenchendo os campos para poder fazer as conversões adequadamente !!!
        User user = new User();
        int id = -1;
        Object idObj = dataObj.get("id");
        if (idObj.equals(null)) { // Erro -- id não pode ser null em um update !!!
            String msg = "Não foi possível fazer 'update' em um registro do tipo 'User' na database. Motivo: Não foi especificado um valor para o campo 'id'. Valor recebido para id = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else id = (Integer)idObj;
        user.setId(id);
        user.setNome(dataObj.getString("nome"));
        user.setSobreNome(dataObj.getString("sobrenome"));
        user.setDatanasIso(dataObj.getString("datanas"));
        user.setSexoInt(dataObj.getInt("sexo"));
        user.setTipoInt(dataObj.getInt("tipo"));
        user.setLogin(dataObj.getString("login"));
        user.setSenha(dataObj.getString("senha"));

        log.debN("Antes de: RetornoCrud ret = user.update("+id+");");
        RetornoCrud ret = user.update(id);

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu criar o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível criar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }

        return jsonEnviado.toString();
    }

    public String deleteUser(JSONObject dataObj) {
        GenericoCrudAux<User> gen = new GenericoCrudAux<User>(new User());
        return gen.deleteGen(dataObj);
    }

    public String deleteAllUser(JSONObject dataObj) {
        GenericoCrudAux<User> gen = new GenericoCrudAux<User>(new User());
        return gen.deleteAllGen(dataObj);
    }

    public String listUser(String condiction) {
        GenericoCrudAux<User> gen = new GenericoCrudAux<User>(new User());
        return gen.listGen(condiction);
    }

    // Recepcionista:
    public String createRecepcionista(JSONObject dataObj) {
        JSONObject jsonEnviado = new JSONObject(); // A resposta !

        log.debN("Antes de: Recepcionista recepcionista = new Recepcionista();");
        // Mais fácil usar o construtor vazio e depois ir preenchendo os campos para poder fazer as conversões adequadamente !!!
        Recepcionista recepcionista = new Recepcionista();
        recepcionista.setId(0);

        //recepcionista.getUser().setId(dataObj.getInt("userId")); // Gera throw em Crud.java se está 'null'
        Object userId = dataObj.get("userId");
        //if (userId == null) {
        if (userId.equals(null)) { // Erro -- userId não pode ser null !!!
            String msg = "Não foi possível criar um registro do tipo 'Recepcionista' na database. Motivo: Não foi especificado um valor para o campo userId. Valor recebido para userId = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        //else recepcionista.getUser().setId(userId); //incompatible types: java.lang.Object cannot be converted to int
        else recepcionista.getUser().setId((Integer)userId);

        log.debN("Antes de: RetornoCrud ret = recepcionista.create();");
        RetornoCrud ret = recepcionista.create();

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu criar o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível criar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }

        return jsonEnviado.toString();
    }


    public String readRecepcionista(JSONObject dataObj) {
        GenericoCrudAux<Recepcionista> gen = new GenericoCrudAux<Recepcionista>(new Recepcionista());
        return gen.readGen(dataObj);
    }


    public String updateRecepcionista(JSONObject dataObj) {
        JSONObject jsonEnviado = new JSONObject(); // A resposta !

        log.debN("Antes de: Recepcionista recepcionista = new Recepcionista();");
        // Mais fácil usar o construtor vazio e depois ir preenchendo os campos para poder fazer as conversões adequadamente !!!
        Recepcionista recepcionista = new Recepcionista();
        int id = -1;
        Object idObj = dataObj.get("id");
        if (idObj.equals(null)) { // Erro -- id não pode ser null em um update !!!
            String msg = "Não foi possível fazer 'update' em um registro do tipo 'Recepcionista' na database. Motivo: Não foi especificado um valor para o campo 'id'. Valor recebido para id = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else id = (Integer)idObj;

        Object userId = dataObj.get("userId");
        if (userId.equals(null)) { // Erro -- userId não pode ser null !!!
            String msg = "Não foi possível criar um registro do tipo 'Recepcionista' na database. Motivo: Não foi especificado um valor para o campo userId. Valor recebido para userId = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else recepcionista.getUser().setId((Integer)userId);

        log.debN("Antes de: RetornoCrud ret = recepcionista.update("+id+");");
        RetornoCrud ret = recepcionista.update(id);

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu criar o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível criar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }

        return jsonEnviado.toString();
    }

    public String deleteRecepcionista(JSONObject dataObj) {
        GenericoCrudAux<Recepcionista> gen = new GenericoCrudAux<Recepcionista>(new Recepcionista());
        return gen.deleteGen(dataObj);
    }

    public String deleteAllRecepcionista(JSONObject dataObj) {
        GenericoCrudAux<Recepcionista> gen = new GenericoCrudAux<Recepcionista>(new Recepcionista());
        return gen.deleteAllGen(dataObj);
    }

    public String listRecepcionista(String condiction) {
        GenericoCrudAux<Recepcionista> gen = new GenericoCrudAux<Recepcionista>(new Recepcionista());
        return gen.listGen(condiction);
    }

    // Paciente:
    public String createPaciente(JSONObject dataObj) {
        JSONObject jsonEnviado = new JSONObject(); // A resposta !

        log.debN("Antes de: Paciente paciente = new Paciente();");
        // Mais fácil usar o construtor vazio e depois ir preenchendo os campos para poder fazer as conversões adequadamente !!!
        Paciente paciente = new Paciente();
        paciente.setId(0);

        //paciente.getUser().setId(dataObj.getInt("userId")); // Gera throw em Crud.java se está 'null'
        Object userId = dataObj.get("userId");
        //if (userId == null) {
        if (userId.equals(null)) { // Erro -- userId não pode ser null !!!
            String msg = "Não foi possível criar um registro do tipo 'Paciente' na database. Motivo: Não foi especificado um valor para o campo userId. Valor recebido para userId = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        //else paciente.getUser().setId(userId); //incompatible types: java.lang.Object cannot be converted to int
        else paciente.getUser().setId((Integer)userId);

        log.debN("Antes de: RetornoCrud ret = paciente.create();");
        RetornoCrud ret = paciente.create();

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu criar o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível criar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }

        return jsonEnviado.toString();
    }


    public String readPaciente(JSONObject dataObj) {
        GenericoCrudAux<Paciente> gen = new GenericoCrudAux<Paciente>(new Paciente());
        return gen.readGen(dataObj);
    }


    public String updatePaciente(JSONObject dataObj) {
        JSONObject jsonEnviado = new JSONObject(); // A resposta !

        log.debN("Antes de: Paciente paciente = new Paciente();");
        // Mais fácil usar o construtor vazio e depois ir preenchendo os campos para poder fazer as conversões adequadamente !!!
        Paciente paciente = new Paciente();
        int id = -1;
        Object idObj = dataObj.get("id");
        if (idObj.equals(null)) { // Erro -- id não pode ser null em um update !!!
            String msg = "Não foi possível fazer 'update' em um registro do tipo 'Paciente' na database. Motivo: Não foi especificado um valor para o campo 'id'. Valor recebido para id = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else id = (Integer)idObj;

        Object userId = dataObj.get("userId");
        if (userId.equals(null)) { // Erro -- userId não pode ser null !!!
            String msg = "Não foi possível criar um registro do tipo 'Paciente' na database. Motivo: Não foi especificado um valor para o campo userId. Valor recebido para userId = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else paciente.getUser().setId((Integer)userId);

        log.debN("Antes de: RetornoCrud ret = paciente.update("+id+");");
        RetornoCrud ret = paciente.update(id);

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu criar o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível criar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }

        return jsonEnviado.toString();
    }

    public String deletePaciente(JSONObject dataObj) {
        GenericoCrudAux<Paciente> gen = new GenericoCrudAux<Paciente>(new Paciente());
        return gen.deleteGen(dataObj);
    }

    public String deleteAllPaciente(JSONObject dataObj) {
        GenericoCrudAux<Paciente> gen = new GenericoCrudAux<Paciente>(new Paciente());
        return gen.deleteAllGen(dataObj);
    }

    public String listPaciente(String condiction) {
        GenericoCrudAux<Paciente> gen = new GenericoCrudAux<Paciente>(new Paciente());
        return gen.listGen(condiction);
    }

    // Exame:
    public String createExame(JSONObject dataObj) {
        JSONObject jsonEnviado = new JSONObject(); // A resposta !

        log.debN("Antes de: Exame exame = new Exame();");
        // Mais fácil usar o construtor vazio e depois ir preenchendo os campos para poder fazer as conversões adequadamente !!!
        Exame exame = new Exame();
        exame.setId(0);

        //exame.getUser().setId(dataObj.getInt("userId")); // Gera throw em Crud.java se está 'null'
        /*Object userId = dataObj.get("pacienteId");
        //if (userId == null) {
        if (userId.equals(null)) { // Erro -- userId não pode ser null !!!
            String msg = "Não foi possível criar um registro do tipo 'Exame' na database. Motivo: Não foi especificado um valor para o campo userId. Valor recebido para userId = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        //else exame.getUser().setId(userId); //incompatible types: java.lang.Object cannot be converted to int
        else exame.getUser().setId((Integer)userId);*/ // Não precisa disto
        exame.setPacienteId(dataObj.getInt("pacienteId"));
        exame.setTipoInt(dataObj.getInt("tipo"));
        exame.setRegiaoInt(dataObj.getInt("regiao"));

        log.debN("Antes de: RetornoCrud ret = exame.create();");
        RetornoCrud ret = exame.create();

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu criar o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível criar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }

        return jsonEnviado.toString();
    }


    public String readExame(JSONObject dataObj) {
        GenericoCrudAux<Exame> gen = new GenericoCrudAux<Exame>(new Exame());
        return gen.readGen(dataObj);
    }


    public String updateExame(JSONObject dataObj) {
        JSONObject jsonEnviado = new JSONObject(); // A resposta !

        log.debN("Antes de: Exame exame = new Exame();");
        // Mais fácil usar o construtor vazio e depois ir preenchendo os campos para poder fazer as conversões adequadamente !!!
        Exame exame = new Exame();
        int id = -1;
        Object idObj = dataObj.get("id");
        if (idObj.equals(null)) { // Erro -- id não pode ser null em um update !!!
            String msg = "Não foi possível fazer 'update' em um registro do tipo 'Exame' na database. Motivo: Não foi especificado um valor para o campo 'id'. Valor recebido para id = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else id = (Integer)idObj;

        /*Object userId = dataObj.get("userId");
        if (userId.equals(null)) { // Erro -- userId não pode ser null !!!
            String msg = "Não foi possível criar um registro do tipo 'Exame' na database. Motivo: Não foi especificado um valor para o campo userId. Valor recebido para userId = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else exame.getUser().setId((Integer)userId);*/ // Não precisa disto !
        exame.setPacienteId(dataObj.getInt("pacienteId"));
        exame.setTipoInt(dataObj.getInt("tipo"));
        exame.setRegiaoInt(dataObj.getInt("regiao"));

        log.debN("Antes de: RetornoCrud ret = exame.update("+id+");");
        RetornoCrud ret = exame.update(id);

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu criar o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível criar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }

        return jsonEnviado.toString();
    }

    public String deleteExame(JSONObject dataObj) {
        GenericoCrudAux<Exame> gen = new GenericoCrudAux<Exame>(new Exame());
        return gen.deleteGen(dataObj);
    }

    public String deleteAllExame(JSONObject dataObj) {
        GenericoCrudAux<Exame> gen = new GenericoCrudAux<Exame>(new Exame());
        return gen.deleteAllGen(dataObj);
    }

    public String listExame(String condiction) {
        GenericoCrudAux<Exame> gen = new GenericoCrudAux<Exame>(new Exame());
        return gen.listGen(condiction);
    }





    // DICOM:
    public String createDICOM(JSONObject dataObj) {
        JSONObject jsonEnviado = new JSONObject(); // A resposta !

        log.debN("Antes de: DICOM dicom = new DICOM();");
        // Mais fácil usar o construtor vazio e depois ir preenchendo os campos para poder fazer as conversões adequadamente !!!
        DICOM dicom = new DICOM();
        dicom.setId(0);

        //dicom.getUser().setId(dataObj.getInt("userId")); // Gera throw em Crud.java se está 'null'
        /*Object userId = dataObj.get("exameId");
        //if (userId == null) {
        if (userId.equals(null)) { // Erro -- userId não pode ser null !!!
            String msg = "Não foi possível criar um registro do tipo 'DICOM' na database. Motivo: Não foi especificado um valor para o campo userId. Valor recebido para userId = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        //else dicom.getUser().setId(userId); //incompatible types: java.lang.Object cannot be converted to int
        else dicom.getUser().setId((Integer)userId);*/ // Não precisa disto
        dicom.setExameId(dataObj.getInt("exameId"));
        dicom.setPastaArq(dataObj.getString("pastaArq"));
        dicom.setNomeArq(dataObj.getString("nomeArq"));

        log.debN("Antes de: RetornoCrud ret = dicom.create();");
        RetornoCrud ret = dicom.create();

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu criar o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível criar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }

        return jsonEnviado.toString();
    }


    public String readDICOM(JSONObject dataObj) {
        GenericoCrudAux<DICOM> gen = new GenericoCrudAux<DICOM>(new DICOM());
        return gen.readGen(dataObj);
    }


    public String updateDICOM(JSONObject dataObj) {
        JSONObject jsonEnviado = new JSONObject(); // A resposta !

        log.debN("Antes de: DICOM dicom = new DICOM();");
        // Mais fácil usar o construtor vazio e depois ir preenchendo os campos para poder fazer as conversões adequadamente !!!
        DICOM dicom = new DICOM();
        int id = -1;
        Object idObj = dataObj.get("id");
        if (idObj.equals(null)) { // Erro -- id não pode ser null em um update !!!
            String msg = "Não foi possível fazer 'update' em um registro do tipo 'DICOM' na database. Motivo: Não foi especificado um valor para o campo 'id'. Valor recebido para id = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else id = (Integer)idObj;

        /*Object userId = dataObj.get("userId");
        if (userId.equals(null)) { // Erro -- userId não pode ser null !!!
            String msg = "Não foi possível criar um registro do tipo 'DICOM' na database. Motivo: Não foi especificado um valor para o campo userId. Valor recebido para userId = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else dicom.getUser().setId((Integer)userId);*/ // Não precisa disto !
        dicom.setExameId(dataObj.getInt("exameId"));
        dicom.setPastaArq(dataObj.getString("pastaArq"));
        dicom.setNomeArq(dataObj.getString("nomeArq"));

        log.debN("Antes de: RetornoCrud ret = dicom.update("+id+");");
        RetornoCrud ret = dicom.update(id);

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu criar o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível criar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }

        return jsonEnviado.toString();
    }

    public String deleteDICOM(JSONObject dataObj) {
        GenericoCrudAux<DICOM> gen = new GenericoCrudAux<DICOM>(new DICOM());
        return gen.deleteGen(dataObj);
    }

    public String deleteAllDICOM(JSONObject dataObj) {
        GenericoCrudAux<DICOM> gen = new GenericoCrudAux<DICOM>(new DICOM());
        return gen.deleteAllGen(dataObj);
    }

    public String listDICOM(String condiction) {
        GenericoCrudAux<DICOM> gen = new GenericoCrudAux<DICOM>(new DICOM());
        return gen.listGen(condiction);
    }


    // Medico:
    public String createMedico(JSONObject dataObj) {
        JSONObject jsonEnviado = new JSONObject(); // A resposta !

        log.debN("Antes de: Medico medico = new Medico();");
        // Mais fácil usar o construtor vazio e depois ir preenchendo os campos para poder fazer as conversões adequadamente !!!
        Medico medico = new Medico();
        medico.setId(0);
        //medico.setNumeroRegistro(dataObj.getInt("numeroRegistro")); // Gera throw em Crud.java se está 'null'
        Object numeroRegistro = dataObj.get("numeroRegistro");
        //if (numeroRegistro == null) {
        if (numeroRegistro.equals(null)) {
            medico.setNumeroRegistro(0);
        }
        //else medico.setNumeroRegistro(numeroRegistro); //incompatible types: java.lang.Object cannot be converted to int
        else medico.setNumeroRegistro((Integer)numeroRegistro);

        medico.setEstadoConselhoStr(dataObj.getString("estadoConselho")); // Porque o cliente não manda os códigos de estado e sim a sigla ou nome por extenso
        //medico.getUser().setId(dataObj.getInt("userId")); // Gera throw em Crud.java se está 'null'
        Object userId = dataObj.get("userId");
        //if (userId == null) {
        if (userId.equals(null)) { // Erro -- userId não pode ser null !!!
            String msg = "Não foi possível criar um registro do tipo 'Medico' na database. Motivo: Não foi especificado um valor para o campo userId. Valor recebido para userId = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        //else medico.getUser().setId(userId); //incompatible types: java.lang.Object cannot be converted to int
        else medico.getUser().setId((Integer)userId);

        log.debN("Antes de: RetornoCrud ret = medico.create();");
        RetornoCrud ret = medico.create();

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu criar o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível criar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }

        return jsonEnviado.toString();
    }


    public String readMedico(JSONObject dataObj) {
        GenericoCrudAux<Medico> gen = new GenericoCrudAux<Medico>(new Medico());
        return gen.readGen(dataObj);
        //return GenericoCrudAux.readGen(new Medico(), dataObj);

        /*JSONObject jsonEnviado = new JSONObject(); // A resposta !
        log.debN("Antes de: User user = new User();");
        User user = new User(); // Usa o construtor vazio pois vai criar um usuário -- com construtor vazio não está dando certo !
        log.debN("Antes de: RetornoCrud ret = user.read();");
        RetornoCrud ret = user.read(dataObj.getInt("id"));

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu LER o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            /*jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível LER um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            /*jsonEnviado.put("status", "Error");
            String msg = "Não foi possível ler um registro do tipo 'User' na database. Motivo: ";
            jsonEnviado.put("msg", msg+ret.getMsg());
            jsonEnviado.put("data", "{}");
        }
        return jsonEnviado.toString();*/
    }


public String updateMedico(JSONObject dataObj) {
        JSONObject jsonEnviado = new JSONObject(); // A resposta !

        log.debN("Antes de: Medico medico = new Medico();");
        // Mais fácil usar o construtor vazio e depois ir preenchendo os campos para poder fazer as conversões adequadamente !!!
        Medico medico = new Medico();
        int id = -1;
        Object idObj = dataObj.get("id");
        if (idObj.equals(null)) { // Erro -- id não pode ser null em um update !!!
            String msg = "Não foi possível fazer 'update' em um registro do tipo 'Medico' na database. Motivo: Não foi especificado um valor para o campo 'id'. Valor recebido para id = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else id = (Integer)idObj;

        Object numeroRegistro = dataObj.get("numeroRegistro");
        if (numeroRegistro.equals(null)) {
            medico.setNumeroRegistro(0);
        }
        else medico.setNumeroRegistro((Integer)numeroRegistro);

        medico.setEstadoConselhoStr(dataObj.getString("estadoConselho")); // Porque o cliente não manda os códigos de estado e sim a sigla ou nome por extenso
        Object userId = dataObj.get("userId");
        if (userId.equals(null)) { // Erro -- userId não pode ser null !!!
            String msg = "Não foi possível criar um registro do tipo 'Medico' na database. Motivo: Não foi especificado um valor para o campo userId. Valor recebido para userId = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else medico.getUser().setId((Integer)userId);

        log.debN("Antes de: RetornoCrud ret = medico.update("+id+");");
        RetornoCrud ret = medico.update(id);

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu criar o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível criar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }

        return jsonEnviado.toString();
    }

    public String deleteMedico(JSONObject dataObj) {
        GenericoCrudAux<Medico> gen = new GenericoCrudAux<Medico>(new Medico());
        return gen.deleteGen(dataObj);
        //return GenericoCrudAux.deleteGen(new Medico(), dataObj);

        /*JSONObject jsonEnviado = new JSONObject(); // A resposta !
        log.debN("Antes de: User user = new User();");
        User user = new User(); // Usa o construtor vazio pois vai DELETAR um usuário -- com construtor vazio não está dando certo !
        log.debN("Antes de: RetornoCrud ret = user.delete();");
        RetornoCrud ret = user.delete(dataObj.getInt("id"));

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu DELETAR o objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            /*jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível LER um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            /*jsonEnviado.put("status", "Error");
            String msg = "Não foi possível deletar um registro do tipo 'User' na database. Motivo: ";
            jsonEnviado.put("msg", msg+ret.getMsg());
            jsonEnviado.put("data", "{}");
        }
        return jsonEnviado.toString();*/
    }

    public String deleteAllMedico(JSONObject dataObj) {
        GenericoCrudAux<Medico> gen = new GenericoCrudAux<Medico>(new Medico());
        return gen.deleteAllGen(dataObj);

        /*JSONObject jsonEnviado = new JSONObject(); // A resposta !
        log.debN("Antes de: Medico medico = new Medico();");
        Medico medico = new Medico(); // Usa o construtor vazio pois vai DELETAR um usuário -- com construtor vazio não está dando certo !
        log.debN("Antes de: RetornoCrud ret = medico.deleteAll();");
        RetornoCrud ret = medico.deleteAll();

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu DELETAR TODOS os registros
            /*"{"status":"OK",
            "msg": "",
            "data": {}
            }"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            /*jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível DELETAR TODOS os registros do tipo "Medico" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            /*jsonEnviado.put("status", "Error");
            String msg = "Não foi possível deletar todos os registros do tipo 'Medico' na database. Motivo: ";
            jsonEnviado.put("msg", msg+ret.getMsg());
            jsonEnviado.put("data", "{}");
        }
        return jsonEnviado.toString());*/
    }

    public String listMedico(String condiction) {
        GenericoCrudAux<Medico> gen = new GenericoCrudAux<Medico>(new Medico());
        return gen.listGen(condiction);

        /*JSONObject jsonEnviado = new JSONObject(); // A resposta !
        log.debN("Antes de: Medico medico = new Medico();");
        Medico medico = new Medico(); // Usa o construtor vazio pois vai criar um usuário -- com construtor vazio não está dando certo !
        log.debN("Antes de: RetornoCrud ret = medico.list("+condiction+");");
        RetornoCrud ret = medico.list(condiction);

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu LER o novo objeto
            /*"{"status":"OK",
            "msg": "",
            "data": {
            "id":1,
            "nome": "Paulo",
            "sobreNome":"Griebler Júnior,
            "datanas": "28/03/1974",
            "sexo": 1,
            "login":"griebler",
            "senha":"12345678"
            }}"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            /*jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível LER um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            /*jsonEnviado.put("status", "Error");
            String msg = "Não foi possível listar registros do tipo 'Medico' na database. Motivo: ";
            jsonEnviado.put("msg", msg+ret.getMsg());
            jsonEnviado.put("data", "{}");
        }
        return jsonEnviado.toString();*/
    }
}
