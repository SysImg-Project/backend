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

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.SessionCookieConfig;

import java.io.IOException;
import java.util.Map;
import java.lang.Integer;
import java.lang.String;
import java.util.Enumeration; // Para Enumeration<String>
import java.io.BufferedReader; // Para BufferedReader 	getReader() Retrieves the body of the request as character data using a BufferedReader.
import java.util.stream.Stream; // Para toArray()

@WebServlet(name = "Requisicao", urlPatterns = {"/endpoint/requisicao"})
public class Requisicao extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getOutputStream();

        out.print("Analisador de Requisições:\n");

        // Método da interface: Servlet que é implementada por HttpServlet retirado de:  https://jakarta.ee/specifications/servlet/5.0/apidocs/jakarta/servlet/servlet --> mudou para 5.0 em vez de 6.0 por causa do Jetty que não pode ser 12.0 porque só plugins para o Maven com o Jetty 11.0
        //ServletConfig 	getServletConfig() Returns a ServletConfig object, which contains initialization and startup parameters for this servlet.
        ServletConfig srvcfg = this.getServletConfig();

        // Métodos da interface: ServletConfig:
        //String 	getInitParameter​(String name) Gets the value of the initialization parameter with the given name.
        //Enumeration<String> 	getInitParameterNames() Returns the names of the servlet's initialization parameters as an Enumeration of String objects, or an empty Enumeration if the servlet has no initialization parameters.
        out.print("ServletConfig:\n");
        Enumeration<String> cfgNomes = srvcfg.getInitParameterNames();
        //if (cfgNomes == null) out.print("srvcfg.getInitParameterNames() = null\n");
        //if (cfgNomes.length == 0) out.print("srvcfg.getInitParameterNames() = null\n"); java.util.Enumeration<java.lang.String> não tem o método 'length'
        if (!cfgNomes.hasMoreElements()) out.print("srvcfg.getInitParameterNames() = não tem mais elementos ! -> tá vazio !\n");
        while (cfgNomes.hasMoreElements()) {
            //out.print("Init. - Nome ="+nomes.nextElement()+"\n");
            String nome = cfgNomes.nextElement();
            String valor = srvcfg.getInitParameter​(nome);
            out.print("Init - Nome ="+nome+" ==> "+valor+"\n");
        };

        //ServletContext 	getServletContext() Returns a reference to the ServletContext in which the caller is executing.
        out.print("ServletConfig.getServletContext() ="+srvcfg.getServletContext()+"\n");
        //String 	getServletName() Returns the name of this servlet instance.
        out.print("ServletConfig.getServletName() ="+srvcfg.getServletName()+"\n");



        // Métodos da interface: HttpServletRequest retirada de: https://jakarta.ee/specifications/servlet/6.0/apidocs/jakarta.servlet/jakarta/servlet/servletrequest

        //AsyncContext 	getAsyncContext() Gets the AsyncContext that was created or reinitialized by the most recent invocation of startAsync() or startAsync(ServletRequest,ServletResponse) on this request.

        //Object 	getAttribute​(String name) Returns the value of the named attribute as an Object, or null if no attribute of the given name exists.

        //Enumeration<String> 	getAttributeNames() Returns an Enumeration containing the names of the attributes available to this request.

        //String 	getCharacterEncoding() Returns the name of the character encoding used in the body of this request.
        out.print("getCharacterEncoding() ="+request.getCharacterEncoding()+"\n");
        //int 	getContentLength() Returns the length, in bytes, of the request body and made available by the input stream, or -1 if the length is not known or is greater than Integer.MAX_VALUE.
        out.print("getContentLength() ="+request.getContentLength()+"\n");
        //long 	getContentLengthLong() Returns the length, in bytes, of the request body and made available by the input stream, or -1 if the length is not known.
        out.print("getContentLengthLong() ="+request.getContentLengthLong()+"\n");
        //String 	getContentType() Returns the MIME type of the body of the request, or null if the type is not known.
        out.print("getContentType() ="+request.getContentType()+"\n");
        //DispatcherType 	getDispatcherType() Gets the dispatcher type of this request.

        //ServletInputStream 	getInputStream() Retrieves the body of the request as binary data using a ServletInputStream.

        //String 	getLocalAddr() Returns the Internet Protocol (IP) address representing the interface on which the request was received.
        out.print("getLocalAddr() ="+request.getLocalAddr()+"\n");
        //Locale 	getLocale() Returns the preferred Locale that the client will accept content in, based on the Accept-Language header.

        //Enumeration<Locale> 	getLocales() Returns an Enumeration of Locale objects indicating, in decreasing order starting with the preferred locale, the locales that are acceptable to the client based on the Accept-Language header.

        //String 	getLocalName() Returns the fully qualified name of the address returned by getLocalAddr().
        out.print("getLocalName() ="+request.getLocalName()+"\n");
        //int 	getLocalPort() Returns the Internet Protocol (IP) port number representing the interface on which the request was received.
        out.print("getLocalPort() ="+request.getLocalPort()+"\n");
        //String 	getParameter​(String name) Returns the value of a request parameter as a String, or null if the parameter does not exist.
        //out.print("getParameter() ="+request.getParameter()"\n");
        //Map<String,​String[]> 	getParameterMap() Returns a java.util.Map of the parameters of this request.
        //Map<String, String> map = request.getParameterMap(); //cannot find symbol [ERROR]   symbol:   method getParameterMap()/
        //for (Map.Entry<String,String> entry : map.entrySet()) out.print("Key = "+entry.getKey()+", Value = "+entry.getValue()+"\n");

        //Enumeration<String> 	getParameterNames() Returns an Enumeration of String objects containing the names of the parameters contained in this request.
        //Ex.: http://168.0.126.147:61498/app/endpoint/requisicao?nome=paulo&sobrenome=griebler --> parameterName = 'nome' parameterValue = 'paulo'
        Enumeration<String> nomes = request.getParameterNames();
        //for (Enumeration<String> e = nomes.elements(); e.hasMoreElements();) out.print("Nome ="+e.nextElement()+"\n"); // Pq não existe o método 'elements()' em Enumeration !
        //for (nomes.hasMoreElements(); out.print("Nome ="+nomes.nextElement()+"\n";));
        while (nomes.hasMoreElements()) {
            out.print("Nome ="+nomes.nextElement()+"\n");
        };
         //while (aenum.hasMoreElements()) { System.out.println(aenum.nextElement()); }

        //String[] 	getParameterValues​(String name) Returns an array of String objects containing all of the values the given request parameter has, or null if the parameter does not exist.
        String[] valores = request.getParameterValues("nome"); //Ex.: http://168.0.126.147:61498/app/endpoint/requisicao?nome=paulo&sobrenome=griebler
        out.print("getParameterValues() ="+"\n");
        //if (valores.length > 0) { // java.lang.NullPointerException: Cannot read the array length because "valores" is null()
        //if (valores) { // incompatible types: java.lang.String[] cannot be converted to boolean
         if (valores != null) {
            for (String s : valores) {
                out.print("valor ="+s+"\n");
            }
        }

        //String 	getProtocol() Returns the name and version of the protocol the request uses in the form protocol/majorVersion.minorVersion, for example, HTTP/1.1.
        out.print("getProtocol() ="+request.getProtocol()+"\n");
        //String 	getProtocolRequestId() Obtain the request identifier for this request as defined by the protocol in use.
        //out.print("getProtocolRequestId() ="+request.getProtocolRequestId()+"\n"); //java.lang.NoSuchMethodError: 'java.lang.String jakarta.servlet.http.HttpServletRequest.getProtocolRequestId()' --> Pq ??? -- Talvez versão da Jetty incompatível com versão do jakarta.servlet ?
        //BufferedReader 	getReader() Retrieves the body of the request as character data using a BufferedReader.
        // --> Usado para ler o JSON que o frontend vai enviar !
        BufferedReader buf = request.getReader();
        out.print("getReader() = Conteúdo do 'body' do 'request'\n");
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
        for(Object o : objects){
            //public static String valueOf(Object obj)
            out.print(o.toString()+"\n");
            System.out.print(o.toString()+"\n"); // Mostra no console -- para testes de requisição
        }

        //String 	getRemoteAddr() Returns the Internet Protocol (IP) of the remote end of the connection on which the request was received.
        out.print("getRemoteAddr() ="+request.getRemoteAddr()+"\n");
        //String 	getRemoteHost() Returns the fully qualified name of the address returned by getRemoteAddr().
        out.print("getRemoteHost() ="+request.getRemoteHost()+"\n");
        //int 	getRemotePort() Returns the Internet Protocol (IP) source port the remote end of the connection on which the request was received.
        out.print("getRemotePort() ="+request.getRemotePort()+"\n");
        //RequestDispatcher 	getRequestDispatcher​(String path) Returns a RequestDispatcher object that acts as a wrapper for the resource located at the given path.

        //String 	getRequestId() Obtain a unique (within the lifetime of the Servlet container) identifier string for this request.
        //out.print("getRequestId() ="+request.getRequestId()+"\n"); // java.lang.NoSuchMethodError: 'java.lang.String jakarta.servlet.http.HttpServletRequest.getRequestId()'
        //String 	getScheme() Returns the name of the scheme used to make this request, for example, http, https, or ftp.
        out.print("getScheme() ="+request.getScheme()+"\n");
        //String 	getServerName() Returns the host name of the server to which the request was sent.
        out.print("getServerName() ="+request.getServerName()+"\n");
        //int 	getServerPort() Returns the port number to which the request was sent.
        out.print("getServerPort() ="+request.getServerPort()+"\n");
        //ServletConnection 	getServletConnection() Obtain details of the network connection to the Servlet container that is being used by this request.

        //ServletContext 	getServletContext() Gets the servlet context to which this ServletRequest was last dispatched.

        //boolean 	isAsyncStarted() Checks if this request has been put into asynchronous mode.
        out.print("isAsyncStarted() ="+request.isAsyncStarted()+"\n");
        //boolean 	isAsyncSupported() Checks if this request supports asynchronous operation.
        out.print("isAsyncSupported() ="+request.isAsyncSupported()+"\n");
        //boolean 	isSecure() Returns a boolean indicating whether this request was made using a secure channel, such as HTTPS.
        out.print("isSecure() ="+request.isSecure()+"\n");
        //void 	removeAttribute​(String name) Removes an attribute from this request.

        //void 	setAttribute​(String name, Object o) Stores an attribute in this request.

        //void 	setCharacterEncoding​(String env) Overrides the name of the character encoding used in the body of this request.

        //AsyncContext 	startAsync() Puts this request into asynchronous mode, and initializes its AsyncContext with the original (unwrapped) ServletRequest and ServletResponse objects.

        //AsyncContext 	startAsync​(ServletRequest servletRequest, ServletResponse servletResponse) Puts this request into asynchronous mode, and initializes its AsyncContext with the given request and response objects.





























        //ServletContext	getServletContext()	Returns a reference to the ServletContext in which the caller is executing.
        ServletContext servletContext = this.getServletContext();

        //SessionCookieConfig	getSessionCookieConfig()	Gets the SessionCookieConfig object through which various properties of the session tracking cookies created on behalf of this ServletContext may be configured.
        SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
        System.out.println("###DEBUG: sessionCookieConfig.toString() ="+sessionCookieConfig.toString()); // Debug -- Resultado:  org.eclipse.jetty.server.session.SessionHandler$CookieConfig@39f89533

       /*//Map<String,​String>	getAttributes()		Obtain the Map (keys are case insensitive) of all attributes and values, including those set via the attribute specific setters, (excluding version) for this SessionCookieConfig.
        //Map<String, ​String> map = sessionCookieConfig.getAttributes();
        //Map<String, String> map = sessionCookieConfig.getAttributes();
        //String	getAttribute​(String name)	Obtain the value for a given session cookie attribute.
        //String count = sessionCookieConfig.getAttribute("Visitas");
        String count;
        count = new String("1"); // Debug
        System.out.println("###DEBUG: Visitas ="+count); // Debug
        String visitas = new String("Visitas");
        //count = sessionCookieConfig.getAttribute("Visitas");
        count = sessionCookieConfig.getAttribute(visitas);// HTTP ERROR 500 java.lang.NoSuchMethodError: 'java.lang.String jakarta.servlet.SessionCookieConfig.getAttribute(java.lang.String)' -- Sem explicação para esse errro, pois no javadoc.io ele existe ! //https://javadoc.io/static/jakarta.servlet/jakarta.servlet-api/6.1.0-M2/jakarta.servlet/jakarta/servlet/SessionCookieConfig.html
        // EM TESTES ! */

        //String	getDomain()	Gets the domain name that will be assigned to any session tracking cookies created on behalf of the application represented by the ServletContext from which this SessionCookieConfig was acquired.
        String domain = sessionCookieConfig.getDomain();
        System.out.println("###DEBUG: sessionCookieConfig.getDomain() ="+domain); // Debug


        //Map<String,​String>	getAttributes()		Obtain the Map (keys are case insensitive) of all attributes and values, including those set via the attribute specific setters, (excluding version) for this SessionCookieConfig.
        //Map<String, ​String> map = sessionCookieConfig.getAttributes(); //[ERROR] /mnt/astrolabium/var/www/Unisinos/Atividade_Acadêmica/2024-1/Estágio/SysImg/Teste_Roots_Paulo/inicio/src/main/java/com/gbl/app/HelloServlet2.java:[54,21] illegal character: '\u200b'

        //Map<String, String> map = sessionCookieConfig.getAttributes(); // Reescrevi manualmente (sem copiar de sites) e funcionou -- provavelmente tem algum caractere a mais escondido aí... -- Mas continua dando (agora outro erro!): HTTP ERROR 500 java.lang.NoSuchMethodError: 'java.util.Map jakarta.servlet.SessionCookieConfig.getAttributes()'

        //public interface Map<K,V> An object that maps keys to values. A map cannot contain duplicate keys; each key can map to at most one value.
        /*for (Map.Entry<String,String> entry : map.entrySet())
            System.out.println("Key = " + entry.getKey() +
                             ", Value = " + entry.getValue());*/

        /*//String count = "";
        for (Map.Entry<String,String> entry : map.entrySet()) {
					System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
					if (entry.getKey().equals("Visitas")) {
						int countInt = Integer.parseInt(entry.getKey());
						//void	setAttribute​(String name, String value)		Sets the value for the given session cookie attribute.
						sessionCookieConfig.setAttribute("Visitas", Integer.toString(countInt++)); // Aumenta o cookie de contagem de visitas
					}
				}*/


        //int	getMaxAge() Gets the lifetime (in seconds) of the session tracking cookies created on behalf of the application represented by the ServletContext from which this SessionCookieConfig was acquired.
        //String	getName() Gets the name that will be assigned to any session tracking cookies created on behalf of the application represented by the ServletContext from which this SessionCookieConfig was acquired.
        //String	getPath() Gets the path that will be assigned to any session tracking cookies created on behalf of the application represented by the ServletContext from which this SessionCookieConfig was acquired.
        //boolean	isHttpOnly() Checks if the session tracking cookies created on behalf of the application represented by the ServletContext from which this SessionCookieConfig was acquired will be marked as HttpOnly.
        //boolean	isSecure()	Checks if the session tracking cookies created on behalf of the application represented by the ServletContext from which this SessionCookieConfig was acquired will be marked as secure even if the request that initiated the corresponding session is using plain HTTP instead of HTTPS.

        //sessionCookieConfig.getAttribute()

        int maxAge = sessionCookieConfig.getMaxAge();
        System.out.println("###DEBUG: sessionCookieConfig.getMaxAge() ="+maxAge); //Debug -- ###DEBUG: sessionCookieConfig.getMaxAge() =-1

        String name = sessionCookieConfig.getName();
        System.out.println("###DEBUG: sessionCookieConfig.getName() ="+name); //Debug -- ###DEBUG: sessionCookieConfig.getName() =JSESSIONID

        String path = sessionCookieConfig.getPath();
        System.out.println("###DEBUG: sessionCookieConfig.getPath() ="+path); //Debug -- ###DEBUG: sessionCookieConfig.getPath() =null

        boolean isHttpOnly = sessionCookieConfig.isHttpOnly();
        System.out.println("###DEBUG: sessionCookieConfig.isHttpOnly() ="+isHttpOnly); //Debug -- ###DEBUG: sessionCookieConfig.isHttpOnly() =false

        boolean isSecure = sessionCookieConfig.isSecure();
        System.out.println("###DEBUG: sessionCookieConfig.isSecure() ="+isSecure); //Debug -- ###DEBUG: sessionCookieConfig.isSecure() =false








    }
}
