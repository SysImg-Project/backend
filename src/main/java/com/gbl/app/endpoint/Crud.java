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

            JSONObject jsonEnviado = new JSONObject(); // A resposta ! // DELETAR
            CrudAux crudAux = new CrudAux();

            switch(action) {
                case "create": {
                    log.debN("Entrou em: case \"create\":");
                    JSONObject dataObj = jsonRecebido.getJSONObject("data");
                    switch (object) {
                        case "user": {
                            out.print(crudAux.createUser(dataObj));
                            return;
                        }
                        case "recepcionista": {
                            out.print(crudAux.createRecepcionista(dataObj));
                            return;
                        }
                        case "paciente": {
                            out.print(crudAux.createPaciente(dataObj));
                            return;
                        }
                        case "exame": {
                            out.print(crudAux.createExame(dataObj));
                            return;
                        }
                        case "dicom": {
                            out.print(crudAux.createDICOM(dataObj));
                            return;
                        }
                        case "medico": {
                            out.print(crudAux.createMedico(dataObj));
                            return;
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
                    // break; unreachable statement
                }
                case "read": {
                    log.debN("Entrou em: case \"read\":");
                    JSONObject dataObj = jsonRecebido.getJSONObject("data");
                    switch (object) {
                        case "user": {
                            out.print(crudAux.readUser(dataObj));
                            return;
                        }
                        case "recepcionista": {
                            out.print(crudAux.readRecepcionista(dataObj));
                            return;
                        }
                        case "paciente": {
                            out.print(crudAux.readPaciente(dataObj));
                            return;
                        }
                        case "exame": {
                            out.print(crudAux.readExame(dataObj));
                            return;
                        }
                        case "dicom": {
                            out.print(crudAux.readDICOM(dataObj));
                            return;
                        }
                        case "medico": {
                            out.print(crudAux.readMedico(dataObj));
                            return;
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
                            out.print(crudAux.updateUser(dataObj));
                            return;
                        }
                        case "recepcionista": {
                            out.print(crudAux.updateRecepcionista(dataObj));
                            return;
                        }
                        case "paciente": {
                            out.print(crudAux.updatePaciente(dataObj));
                            return;
                        }
                        case "exame": {
                            out.print(crudAux.updateExame(dataObj));
                            return;
                        }
                        case "dicom": {
                            out.print(crudAux.updateDICOM(dataObj));
                            return;
                        }
                        case "medico": {
                            out.print(crudAux.updateMedico(dataObj));
                            return;
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
                            out.print(crudAux.deleteUser(dataObj));
                            return;
                        }
                        case "recepcionista": {
                            out.print(crudAux.deleteRecepcionista(dataObj));
                            return;
                        }
                        case "paciente": {
                            out.print(crudAux.deletePaciente(dataObj));
                            return;
                        }
                        case "exame": {
                            out.print(crudAux.deleteExame(dataObj));
                            return;
                        }
                        case "dicom": {
                            out.print(crudAux.deleteDICOM(dataObj));
                            return;
                        }
                        case "medico": {
                            out.print(crudAux.deleteMedico(dataObj));
                            return;
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
                            out.print(crudAux.deleteAllUser(dataObj));
                            return;
                        }
                        case "recepcionista": {
                            out.print(crudAux.deleteAllRecepcionista(dataObj));
                            return;
                        }
                        case "paciente": {
                            out.print(crudAux.deleteAllPaciente(dataObj));
                            return;
                        }
                        case "exame": {
                            out.print(crudAux.deleteAllExame(dataObj));
                            return;
                        }
                        case "dicom": {
                            out.print(crudAux.deleteAllDICOM(dataObj));
                            return;
                        }
                        case "medico": {
                            out.print(crudAux.deleteAllMedico(dataObj));
                            return;
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
                    String condiction = jsonRecebido.getString("condiction"); // Só aqui em 'list' é que 'data' que vem no JSON recebido do cliente é tratado como String. Em todos as outras ações 'data' é um objeto JSON
                    switch (object) {
                        case "user": {
                            out.print(crudAux.listUser(condiction));
                            return;
                        }
                        case "recepcionista": {
                            out.print(crudAux.listRecepcionista(condiction));
                            return;
                        }
                        case "paciente": {
                            out.print(crudAux.listPaciente(condiction));
                            return;
                        }
                        case "exame": {
                            out.print(crudAux.listExame(condiction));
                            return;
                        }
                        case "dicom": {
                            out.print(crudAux.listDICOM(condiction));
                            return;
                        }
                        case "medico": {
                            out.print(crudAux.listMedico(condiction));
                            return;
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
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.out.println(ste + "\n");
            }
        }
    }
}
