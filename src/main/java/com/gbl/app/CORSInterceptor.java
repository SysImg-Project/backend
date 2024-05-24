package com.gbl.app;

import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.ServletException; // Para throws ServletException

import java.io.IOException;

import java.util.Collection; // Para: Collection<String> headers = resp.getHeaderNames();


//https://stackoverflow.com/questions/58501852/cors-enable-in-servlet
@WebFilter(asyncSupported = true, urlPatterns = { "/*" }) // Vale para todo o site não só no 'app/endpoint'
public class CORSInterceptor implements Filter {
    /*private static final String[] allowedOrigins = {
            "http://localhost:3000", "http://localhost:5500", "http://localhost:5501",
            "http://127.0.0.1:3000", "http://127.0.0.1:5500", "http://127.0.0.1:5501"
    };*/
    //private static final String[] allowedOrigins = {"*"}; //java.lang.NullPointerException: Cannot invoke "String.equals(Object)" because "origin" is null --> gerou este erro porque coloquei {"*"} -- o método 'isAllowedOrigin()' não foi feito para trabalhar com isso ! --> aceita strings tipo "http://localhost:3000" mas não "*"

    private static final String[] allowedOrigins = {"http://168.0.126.147:65100"}; // Endereço do Teste de Backend do SysImg -- não deu muito certo -- ainda -- java.lang.NullPointerException: Cannot invoke "String.equals(Object)" because "origin" is null at com.gbl.app.CORSInterceptor.isAllowedOrigin (CORSInterceptor.java:57)


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String requestOrigin = request.getHeader("Origin");
        System.out.println("Em com.gbl.app.CORSInterceptor: --> request.getHeader(\"Origin\") ="+request.getHeader("Origin")); // Debug --> request.getHeader("Origin") =null


        //if(isAllowedOrigin(requestOrigin)) { // Aqui autoriza o CORS apenas se a origem for aquela solicitada... Porém se quiser autorizar inicialmente para qualquer lugar (não recomendável !)
        //if(true) { // Autoriza para todos os IPs -- só inicialmente - REVER !
        if(isAllowedOrigin("http://168.0.126.147:65100")) { // Aqui autoriza o CORS apenas se a origem for aquela solicitada... Porém se quiser autorizar inicialmente para qualquer lugar (não recomendável !) --> Coloquei o endereço do cliente
            // Authorize the origin, all headers, and all methods
            //((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", requestOrigin);
            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*"); // Pq 'requestOrigin' == null o que não estava gerando a inserção deste header na resposta para o cliente -- o que no meu ponto de vista bloqueava o CORS !!! ==> NA VERDADE AINDA NÃO É ISSO. O TESTE DE BACKEND CONTINUA NÃO CONSEGUINDO ACESSAR... (11/05/2024)
            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "*");
            ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods",
                    "GET, OPTIONS, HEAD, PUT, POST, DELETE");

            HttpServletResponse resp = (HttpServletResponse) servletResponse;

            // Verifica o que foi adicionado nos headers de resposta para o cliente:
            //https://jakarta.ee/specifications/servlet/6.0/apidocs/jakarta.servlet/jakarta/servlet/http/httpservletresponse
            //De: public interface HttpServletResponse extends ServletResponse:
            //Collection<String> 	getHeaderNames() Gets the names of the headers of this response.
            Collection<String> headers = resp.getHeaderNames();
            String[] headersArray = new String[headers.size()];
            headersArray = headers.toArray(headersArray);
            for (int i = 0; i < headersArray.length; i++) {
                //Collection<String> 	getHeaders​(String name) Gets the values of the response header with the given name. ---> Usar este ?!

                //String 	getHeader​(String name) 	Gets the value of the response header with the given name. ---> OU usar este ?
                String valor = resp.getHeader(headersArray[i]);
                System.out.println("Chave:  "+headersArray[i]+"| Valor: "+valor);
            }



            // CORS handshake (pre-flight request)
            if (request.getMethod().equals("OPTIONS")) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                return;
            }
        }
        // pass the request along the filter chain
        filterChain.doFilter(request, servletResponse);

        // Verifica o que foi adicionado nos headers de resposta para o cliente:
        //https://jakarta.ee/specifications/servlet/6.0/apidocs/jakarta.servlet/jakarta/servlet/http/httpservletresponse
        //De: public interface HttpServletResponse extends ServletResponse:

        //Collection<String> 	getHeaderNames() Gets the names of the headers of this response.

        //Collection<String> 	getHeaders​(String name) Gets the values of the response header with the given name.


    }

    private boolean isAllowedOrigin(String origin){
        for (String allowedOrigin : allowedOrigins) {
            if(origin.equals(allowedOrigin)) return true;
        }
        return false;
    }
}
