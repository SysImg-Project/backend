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
import com.gbl.ICrud;
import org.json.*; // Para JSON
import com.gbl.Log;
import com.gbl.classes.ExcecaoValorInvalido;
import com.gbl.RetornoCrud;
//import com.gbl.classes.User;
//import java.time.LocalDate;
//import com.gbl.classes.Sexo;
//import com.gbl.classes.users.profissionalSaude.Medico;


//public class GenericoCrudAux<T> implements ICrud
public class GenericoCrudAux<T> {

    private Log log; // Instância do objeto Log -- para logs
    private T obj;
    //private (ICrud)T obj2; illegal start of type
    private ICrud obj2;
    private JSONObject jsonEnviado;

    public GenericoCrudAux(T obj) {
        this.log = Log.getInstance(); // Obtêm uma instância do objeto Log -- para logs
        jsonEnviado = new JSONObject(); // A resposta !
        //this.obj = obj;
        if (obj instanceof ICrud) {
            //this.obj = (ICrud)obj; // Faz um downcast -- transforma o objeto por exemplo 'User' em 'ICrud' para poder ter acesso aos métodos de 'ICrud' -- Isso é necessário ? -- incompatible types: com.gbl.ICrud cannot be converted to T
            this.obj2 = (ICrud)obj;
            this.obj = obj;
        }
        else {
            String msg = "'obj' não é uma instância de ICrud e por isso não pode ser usado para chamar os seus métodos.";
            log.erroN(msg);
            throw new ExcecaoValorInvalido(msg);
            //System.exit(1); // unreachable statement -- Pára o programa !
        }
    }

    public <T> String readGen(JSONObject dataObj) { // PQ esse métod não se chama 'read' ? - Para não confundir com o 'read' em ICrud
        int id = -1;
        Object idObj = dataObj.get("id");
        if (idObj.equals(null)) { // Erro -- id não pode ser null em um delete !!!
            //String msg = "Não foi possível fazer 'delete' em um registro do tipo 'Recepcionista' na database. Motivo: Não foi especificado um valor para o campo 'id'. Valor recebido para id = null";
            String msg = "Não foi possível realizar a operação 'read' na database. Motivo: Não foi especificado um valor para o campo 'id'. Valor recebido para id = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else id = (Integer)idObj;
        log.debN("Antes de: RetornoCrud ret = obj.read("+id+");");
        RetornoCrud ret = obj2.read(id);

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
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível LER um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            String msg = "Não foi possível ler um registro do tipo 'User' na database. Motivo: ";
            jsonEnviado.put("msg", msg+ret.getMsg());
            jsonEnviado.put("data", "{}");
        }
        return jsonEnviado.toString();
    }


    public <T> String deleteGen(JSONObject dataObj) {
        int id = -1;
        Object idObj = dataObj.get("id");
        if (idObj.equals(null)) { // Erro -- id não pode ser null em um delete !!!
            //String msg = "Não foi possível fazer 'delete' em um registro do tipo 'Recepcionista' na database. Motivo: Não foi especificado um valor para o campo 'id'. Valor recebido para id = null";
            String msg = "Não foi possível realizar a operação 'delete' na database. Motivo: Não foi especificado um valor para o campo 'id'. Valor recebido para id = null";
            jsonEnviado.put("status", "Error");
            jsonEnviado.put("msg", msg);
            jsonEnviado.put("data", "{}");
            return jsonEnviado.toString(); // Retorno antecipado por erro !
        }
        else id = (Integer)idObj;
        log.debN("Antes de: RetornoCrud ret = obj.delete("+id+");");
        RetornoCrud ret = obj2.delete(id);

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
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível LER um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            String msg = "Não foi possível deletar um registro do tipo 'User' na database. Motivo: ";
            jsonEnviado.put("msg", msg+ret.getMsg());
            jsonEnviado.put("data", "{}");
        }
        return jsonEnviado.toString();
    }

    public <T> String deleteAllGen(JSONObject dataObj) {
        log.debN("Antes de: RetornoCrud ret = obj.deleteAll();");
        RetornoCrud ret = obj2.deleteAll();

        //RetornoCrud ret = new RetornoCrud(); // Debug -- DELETAR !

        //Devolve para o cliente uma resposta em JSON:
        if (ret.getSucesso()) { // OK - conseguiu DELETAR TODOS os registros
            /*"{"status":"OK",
            "msg": "",
            "data": {}
            }"*/ // JSON a ser enviado
            // JSONObject 	put(String key, Object value) Put a key/value pair in the JSONObject
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", "{}");
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível DELETAR TODOS os registros do tipo "Medico" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            String msg = "Não foi possível deletar todos os registros do tipo 'Medico' na database. Motivo: ";
            jsonEnviado.put("msg", msg+ret.getMsg());
            jsonEnviado.put("data", "{}");
        }
        return jsonEnviado.toString();
    }

    public <T> String listGen(String condiction) {
        log.debN("Antes de: RetornoCrud ret = obj.list("+condiction+");");
        RetornoCrud ret = obj2.list(condiction);

        //RetornoCrud ret = new RetornoCrud(); // Debug -- DELETAR !

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
            jsonEnviado.put("status", "OK");
            jsonEnviado.put("msg", ret.getMsg());
            jsonEnviado.put("data", ret.getJson());
        }
        else { // Erro
            /*"{"status":"Error",
            "msg": "Não foi possível LER um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
            "data": {}
            }"*/ // JSON a ser enviado
            jsonEnviado.put("status", "Error");
            //https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Class.html
            //String getName() Returns the name of the entity (class, interface, array class, primitive type, or void) represented by this Class object.

            String msg = "Não foi possível listar registros do tipo '"+obj.getClass().getName()+"' na database. Motivo: ";
            jsonEnviado.put("msg", msg+ret.getMsg());
            jsonEnviado.put("data", "{}");
        }
        return jsonEnviado.toString();
    }
}


