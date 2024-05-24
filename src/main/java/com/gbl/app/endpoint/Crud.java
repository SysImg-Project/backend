package com.gbl.app.endpoint;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.SessionCookieConfig;

import jakarta.servlet.ServletException; // Para throws ServletException

import java.io.IOException;
import java.util.Map;
import java.lang.Integer;
import java.lang.String;
import java.util.Enumeration; // Para Enumeration<String>
import java.io.BufferedReader; // Para BufferedReader 	getReader() Retrieves the body of the request as character data using a BufferedReader.
import java.util.stream.Stream; // Para toArray()
import org.json.*; // Para JSON
import com.gbl.Log;
import com.gbl.RetornoCrud;
import com.gbl.classes.User;
import java.time.LocalDate;
import com.gbl.classes.Sexo;

@WebServlet(name = "Crud", urlPatterns = {"/endpoint/crud"})
public class Crud extends HttpServlet {
    private Log log; // Instância do objeto Log -- para logs

    // Solução para o problema do CORS:
    // https://stackoverflow.com/questions/75848314/java-jetty-server-giving-error-for-blocked-by-cors-policy
    /*protected void doOptions(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {

        System.out.print("Analisador de requisições de CRUD-doOptions:\n");
        //Ex.: http://168.0.126.147:61498/app/endpoint/requisicao?nome=paulo&sobrenome=griebler --> parameterName = 'nome' parameterValue = 'paulo'
        Enumeration<String> nomes = request.getParameterNames();
        //for (Enumeration<String> e = nomes.elements(); e.hasMoreElements();) out.print("Nome ="+e.nextElement()+"\n"); // Pq não existe o método 'elements()' em Enumeration !
        //for (nomes.hasMoreElements(); out.print("Nome ="+nomes.nextElement()+"\n";));
        while (nomes.hasMoreElements()) {
            System.out.print("Nome ="+nomes.nextElement()+"\n");
        };
         //while (aenum.hasMoreElements()) { System.out.println(aenum.nextElement()); }

        //String[] 	getParameterValues​(String name) Returns an array of String objects containing all of the values the given request parameter has, or null if the parameter does not exist.
        String[] valores = request.getParameterValues("nome"); //Ex.: http://168.0.126.147:61498/app/endpoint/requisicao?nome=paulo&sobrenome=griebler
        System.out.print("getParameterValues() ="+"\n");
        //if (valores.length > 0) { // java.lang.NullPointerException: Cannot read the array length because "valores" is null()
        //if (valores) { // incompatible types: java.lang.String[] cannot be converted to boolean
         if (valores != null) {
            for (String s : valores) {
                System.out.print("valor ="+s+"\n");
            }
        }

        //String 	getProtocol() Returns the name and version of the protocol the request uses in the form protocol/majorVersion.minorVersion, for example, HTTP/1.1.
        System.out.print("getProtocol() ="+request.getProtocol()+"\n");
        //String 	getProtocolRequestId() Obtain the request identifier for this request as defined by the protocol in use.
        //out.print("getProtocolRequestId() ="+request.getProtocolRequestId()+"\n"); //java.lang.NoSuchMethodError: 'java.lang.String jakarta.servlet.http.HttpServletRequest.getProtocolRequestId()' --> Pq ??? -- Talvez versão da Jetty incompatível com versão do jakarta.servlet ?
        //BufferedReader 	getReader() Retrieves the body of the request as character data using a BufferedReader.
        // --> Usado para ler o JSON que o frontend vai enviar !
        BufferedReader buf = request.getReader();
        //out.print("getReader() = Conteúdo do 'body' do 'request'\n");
        System.out.print("getReader() = Conteúdo do 'body' do 'request'\n"); // Mostra no console -- para testes de requisição

        //Stream<String> BufferedReader.lines() Returns a Stream, the elements of which are lines read from this BufferedReader.
        Stream<String> stream = buf.lines();

        //Object[] InterfaceStream.toArray() Returns an array containing the elements of this stream.
        //String[] strings = stream.toArray(); //incompatible types: java.lang.Object[] cannot be converted to java.lang.String[]
        //Object<String>[] strings = stream.toArray(); //type java.lang.Object does not take parameters
        //for(String s : strings){
        //  out.print(s+"\n");
        //}
        Object[] objects = stream.toArray();
        for(Object o : objects){
            //public static String valueOf(Object obj)
            //out.print(o.toString()+"\n");
            System.out.print(o.toString()+"\n"); // Mostra no console -- para testes de requisição
        }



        res.setStatus(200);
        //res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Origin", "http://168.0.126.147:65100/");
        res.addHeader("Access-Control-Allow-Headers", "*");
        res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        res.addHeader("Content-Type", "application/json");
        res.getWriter().println("{}");
    }*/ // Acho que este método não faz diferença aqui... Isso é feito no CORSInterceptor.java

    @Override
    //protected void doGet(HttpServletRequest request, HttpServletResponse response) --> Aqui como o cliente manda através de um JSON usando POST deve fazer o Override do método doPost
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.log = Log.getInstance(); // Obtêm uma instância do objeto Log -- para logs
        log.logN("Entrou em: protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException");

        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getOutputStream();

        System.out.print("Analisador de requisições de CRUD:\n");


        //Ex.: http://168.0.126.147:61498/app/endpoint/requisicao?nome=paulo&sobrenome=griebler --> parameterName = 'nome' parameterValue = 'paulo'
        Enumeration<String> nomes = request.getParameterNames();
        //for (Enumeration<String> e = nomes.elements(); e.hasMoreElements();) out.print("Nome ="+e.nextElement()+"\n"); // Pq não existe o método 'elements()' em Enumeration !
        //for (nomes.hasMoreElements(); out.print("Nome ="+nomes.nextElement()+"\n";));
        while (nomes.hasMoreElements()) {
            System.out.print("Nome ="+nomes.nextElement()+"\n");
        };
         //while (aenum.hasMoreElements()) { System.out.println(aenum.nextElement()); }

        //String[] 	getParameterValues​(String name) Returns an array of String objects containing all of the values the given request parameter has, or null if the parameter does not exist.
        String[] valores = request.getParameterValues("nome"); //Ex.: http://168.0.126.147:61498/app/endpoint/requisicao?nome=paulo&sobrenome=griebler
        System.out.print("getParameterValues() ="+"\n");
        //if (valores.length > 0) { // java.lang.NullPointerException: Cannot read the array length because "valores" is null()
        //if (valores) { // incompatible types: java.lang.String[] cannot be converted to boolean
         if (valores != null) {
            for (String s : valores) {
                System.out.print("valor ="+s+"\n");
            }
        }

        //String 	getProtocol() Returns the name and version of the protocol the request uses in the form protocol/majorVersion.minorVersion, for example, HTTP/1.1.
        System.out.print("getProtocol() ="+request.getProtocol()+"\n");
        //String 	getProtocolRequestId() Obtain the request identifier for this request as defined by the protocol in use.
        //out.print("getProtocolRequestId() ="+request.getProtocolRequestId()+"\n"); //java.lang.NoSuchMethodError: 'java.lang.String jakarta.servlet.http.HttpServletRequest.getProtocolRequestId()' --> Pq ??? -- Talvez versão da Jetty incompatível com versão do jakarta.servlet ?
        //BufferedReader 	getReader() Retrieves the body of the request as character data using a BufferedReader.
        // --> Usado para ler o JSON que o frontend vai enviar !
        BufferedReader buf = request.getReader();
        //out.print("getReader() = Conteúdo do 'body' do 'request'\n");
        System.out.print("getReader() = Conteúdo do 'body' do 'request'\n"); // Mostra no console -- para testes de requisição

        //Stream<String> BufferedReader.lines() Returns a Stream, the elements of which are lines read from this BufferedReader.
        Stream<String> stream = buf.lines();

        //Object[] InterfaceStream.toArray() Returns an array containing the elements of this stream.
        //String[] strings = stream.toArray(); //incompatible types: java.lang.Object[] cannot be converted to java.lang.String[]
        //Object<String>[] strings = stream.toArray(); //type java.lang.Object does not take parameters
        /*for(String s : strings){
            out.print(s+"\n");
        }*/
        Object[] objects = stream.toArray();
        JSONObject jsonRecebido = new JSONObject();
        for(Object o : objects){
            //public static String valueOf(Object obj)
            //out.print(o.toString()+"\n");
            System.out.print(o.toString()+"\n"); // Mostra no console -- para testes de requisição
            jsonRecebido = new JSONObject(o.toString()); // Pega a String recebida e cria um objeto JSON
        }

        // A PARTIR DAQUI VAI TRABALHAR COM O JSON PARA DAR UMA RESPOSTA À REQUISIÇÃO:
        //Analisador de requisições de CRUD: <--- O que se obtem no console quando acessa esta URL mandando um POST com JSON
        //getParameterValues() =
        //getProtocol() =HTTP/1.1
        //getReader() = Conteúdo do 'body' do 'request'
        //{"object":"user","action":"create","data":{"id":null,"nome":"","sobrenome":"","datanas":"","sexo":"0","login":"","senha":""}}

        // Algoritmo:
        // - Ver se existe uma 'action' definida --> Se tem uma chama o método da 'action' dentro da classe do 'object' que foi indicada, mandando o objeto 'data' como parâmetro
        //public Object get(String key)  throws JSONException

        try {
            String action = jsonRecebido.getString("action");
            String object = jsonRecebido.getString("object");
            //String data = jsonRecebido.getString("data"); //org.json.JSONException: JSONObject["data"] is not a string (class org.json.JSONObject).
            //JSONObject 	getJSONObject(String key) Get the JSONObject value associated with a key

            JSONObject jsonEnviado = new JSONObject(); // A resposta !

            switch(action) {
                case "create": {
                    log.debN("Entrou em: case \"create\":");
                    JSONObject dataObj = jsonRecebido.getJSONObject("data");
                    switch (object) {
                        case "user": {
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
                            out.print(jsonEnviado.toString());
                            break;
                        }
                        default: {
                            String msg = "###Erro: Em Crud.java: - O 'object' '"+object+"' não existe !";
                            System.out.println(msg);
                            jsonEnviado.put("status", "Error");
                            jsonEnviado.put("msg", msg);
                            jsonEnviado.put("data", "{}");
                            out.print(jsonEnviado.toString());
                            return;
                        }
                    }
                    break;
                }
                case "read": {
                    log.debN("Entrou em: case \"read\":");
                    JSONObject dataObj = jsonRecebido.getJSONObject("data");
                    switch (object) {
                        case "user": {
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
                            out.print(jsonEnviado.toString());
                            return;
                            //break; // unreachable statement
                        }
                        default: {
                            String msg = "###Erro: Em Crud.java: - O 'object' '"+object+"' não existe !";
                            System.out.println(msg);
                            jsonEnviado.put("status", "Error");
                            jsonEnviado.put("msg", msg);
                            jsonEnviado.put("data", "{}");
                            out.print(jsonEnviado.toString());
                            return;
                        }
                    }
                    //break; // unreachable statement
                }
                case "update": {
                    log.debN("Entrou em: case \"update\":");
                    JSONObject dataObj = jsonRecebido.getJSONObject("data");
                    switch (object) {
                        case "user": {
                            log.debN("Antes de: User user = new User();");
                            //LocalDate datanas = LocalDate.parse("0000-01-01"); // "0000-00-00" ==> não dá --> java.time.format.DateTimeParseException: Text '0000-00-00' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 0
                            User user = new User();
                            user.setId(dataObj.getInt("id"));
                            user.setNome(dataObj.getString("nome"));
                            user.setSobreNome(dataObj.getString("sobrenome"));
                            user.setDatanasIso(dataObj.getString("datanas"));
                            user.setSexoInt(dataObj.getInt("sexo"));
                            user.setTipoInt(dataObj.getInt("tipo"));
                            user.setLogin(dataObj.getString("login"));
                            user.setSenha(dataObj.getString("senha"));

                            log.debN("Antes de: RetornoCrud ret = user.update();");
                            RetornoCrud ret = user.update(dataObj.getInt("id"));

                            //Devolve para o cliente uma resposta em JSON:
                            if (ret.getSucesso()) { // OK - conseguiu editar o novo objeto
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
                                "msg": "Não foi possível editar um registro do tipo "Usuario" na database. Motivo: erro de comunicação com a database.",
                                "data": {}
                                }"*/ // JSON a ser enviado
                                jsonEnviado.put("status", "Error");
                                String msg = "Não foi possível editar um registro do tipo 'User' na database. Motivo: ";
                                jsonEnviado.put("msg", msg+ret.getMsg());
                                jsonEnviado.put("data", "{}");
                            }
                            out.print(jsonEnviado.toString());
                            return;
                            //break; // unreachable statement
                        }
                        default: {
                            String msg = "###Erro: Em Crud.java: - O 'object' '"+object+"' não existe !";
                            System.out.println(msg);
                            jsonEnviado.put("status", "Error");
                            jsonEnviado.put("msg", msg);
                            jsonEnviado.put("data", "{}");
                            out.print(jsonEnviado.toString());
                            return;
                        }
                    }
                    //break;  // unreachable statement
                }
                case "delete": {
                    log.debN("Entrou em: case \"delete\":");
                    JSONObject dataObj = jsonRecebido.getJSONObject("data");
                    switch (object) {
                        case "user": {
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
                            out.print(jsonEnviado.toString());
                            return;
                            //break; // unreachable statement
                        }
                        default: {
                            String msg = "###Erro: Em Crud.java: - O 'object' '"+object+"' não existe !";
                            System.out.println(msg);
                            jsonEnviado.put("status", "Error");
                            jsonEnviado.put("msg", msg);
                            jsonEnviado.put("data", "{}");
                            out.print(jsonEnviado.toString());
                            return;
                        }
                    }
                    //break; // unreachable statement
                }
                case "delete_all": {
                    log.debN("Entrou em: case \"delete_all\":");
                    JSONObject dataObj = jsonRecebido.getJSONObject("data");
                    switch (object) {
                        case "user": {
                            log.debN("Antes de: User user = new User();");
                            User user = new User(); // Usa o construtor vazio pois vai DELETAR um usuário -- com construtor vazio não está dando certo !
                            log.debN("Antes de: RetornoCrud ret = user.delete_all();");
                            RetornoCrud ret = user.deleteAll();

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
                                "msg": "Não foi possível DELETAR TODOS os registros do tipo "User" na database. Motivo: erro de comunicação com a database.",
                                "data": {}
                                }"*/ // JSON a ser enviado
                                jsonEnviado.put("status", "Error");
                                String msg = "Não foi possível deletar todos os registros do tipo 'User' na database. Motivo: ";
                                jsonEnviado.put("msg", msg+ret.getMsg());
                                jsonEnviado.put("data", "{}");
                            }
                            out.print(jsonEnviado.toString());
                            return;
                            //break; // unreachable statement
                        }
                        default: {
                            String msg = "###Erro: Em Crud.java: - O 'object' '"+object+"' não existe !";
                            System.out.println(msg);
                            jsonEnviado.put("status", "Error");
                            jsonEnviado.put("msg", msg);
                            jsonEnviado.put("data", "{}");
                            out.print(jsonEnviado.toString());
                            return;
                        }
                    //break; // unreachable statement
                    }
                }
                case "list": {
                    log.debN("Entrou em: case \"list\":");
                    String condiction = jsonRecebido.getString("condiction"); // Só maqui em 'list' é que 'data' que vem no JSON recebido do cliente é tratado como String. Em todos as outras ações 'data' é um objeto JSON
                    switch (object) {
                        case "user": {
                            log.debN("Antes de: User user = new User();");
                            User user = new User(); // Usa o construtor vazio pois vai criar um usuário -- com construtor vazio não está dando certo !
                            log.debN("Antes de: RetornoCrud ret = user.list("+condiction+");");
                            RetornoCrud ret = user.list(condiction);

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
                                String msg = "Não foi possível listar registros do tipo 'User' na database. Motivo: ";
                                jsonEnviado.put("msg", msg+ret.getMsg());
                                jsonEnviado.put("data", "{}");
                            }
                            out.print(jsonEnviado.toString());
                            return;
                            //break; // unreachable statement
                        }
                        default: {
                            String msg = "###Erro: Em Crud.java: - O 'object' '"+object+"' não existe !";
                            System.out.println(msg);
                            jsonEnviado.put("status", "Error");
                            jsonEnviado.put("msg", msg);
                            jsonEnviado.put("data", "{}");
                            out.print(jsonEnviado.toString());
                            return;
                        }
                    }
                    //break; // unreachable statement
                }

                default: {
                    String msg = "###Erro: Em Crud.java: - A 'action' '"+action+"' não existe !";
                    System.out.println(msg);
                    jsonEnviado.put("status", "Error");
                    jsonEnviado.put("msg", msg);
                    jsonEnviado.put("data", "{}");
                    out.print(jsonEnviado.toString());
                    return;
                }
            }
        }
        catch(JSONException e) {
            System.out.println("###Aviso: Houve uma exceção ! --> LOCAL DA EXCEÇÃO : app/endpoint/Crud.java");
            System.out.println(e.toString());
            //for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            //    System.out.println(ste + "\n");
            //}
        }
    }
}
