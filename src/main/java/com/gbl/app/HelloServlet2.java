package com.gbl.app;

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

@WebServlet(name = "HelloServlet2", urlPatterns = {"/hello2"})
public class HelloServlet2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getOutputStream();

        out.print("Hello there from Servlet2");
        
        
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
