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
import java.io.FileNotFoundException;
import java.lang.String;

import java.io.BufferedReader; // Para: BufferedReader 	getReader() Retrieves the body of the request as character data using a BufferedReader.
import java.util.stream.Stream; // Para: toArray()

//import java.nio.file.*; // Para: Path.of(pathTranslated)
import java.io.FileReader;

import com.gbl.Log;

// Exemplo: http://192.168.0.20:4100/app/endpoint/staticfiles/dicom/TC_Abd/series-00000/image-00000.dcm --> Vai receber o código deste DICOM no browser
@WebServlet(name = "StaticFiles", urlPatterns = {"/endpoint/staticfiles/*"})
public class StaticFiles extends HttpServlet {
    private Log log; // Instância do objeto Log -- para logs

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        this.log = Log.getInstance(); // Obtêm uma instância do objeto Log -- para logs
        log.logN("Entrou em: StaticFiles.java -- protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException");

        //https://jakarta.ee/specifications/servlet/6.0/apidocs/jakarta.servlet/jakarta/servlet/http/httpservletrequest#getContextPath()
        //String public interface HttpServletRequest.getContextPath()

        String contextPath = request.getContextPath();
        log.debN("contextPath ="+contextPath); // Debug // Só "/app" -- não mostra o resto da URL !

        //String HttpServletRequest.getPathInfo()
        String pathInfo = request.getPathInfo();
        // URL: http://192.168.0.20:4100/app/endpoint/staticfiles/series/0001.dcm
        log.debN("pathInfo ="+pathInfo); // "/series/0001.dcm"

        //String HttpServletRequest.getPathTranslated()
        String pathTranslated = request.getPathTranslated();
        // URL: http://192.168.0.20:4100/app/endpoint/staticfiles/series/0001.dcm
        log.debN("pathTranslated ="+pathTranslated); // /home/griebler/Rsync/Unisinos/Atividade_Academica/2024-1/Estagio/SysImg/codigo/backend/v99_1_0/src/main/webapp/series/0001.dcm

        // Com a info acima, já dá para procurar no sistema de arquivos se existe o arquivo solicitado e se ele existe manda-o na resposta.

        //Path path = FileSystems.getDefault().getPath("logs", "access.log");
        //https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Path.html
        //static Path of(String first, String... more)
        /*Path path = Path.of(pathTranslated);
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);*/

        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getOutputStream();

        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(pathTranslated));
        }
        catch (FileNotFoundException e) {
            // Não faz nada -- deixa 'in' = null que o if abaixo vai absorver essa exceção
        }
        catch (IOException e) {
            log.erroN("Houve uma exceção ! --> LOCAL DA EXCEÇÃO : app/endpoint/StaticFiles.java");
            log.erroN(e.toString());
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                log.erroN(ste + "\n");
            }
        }

        if (in == null) { // Evita o erro: Cannot invoke "java.io.BufferedReader.lines()" because "in" is null -- que pode acontecer no caso de que o arquivo solicitado não exista
            //void 	setStatus​(int sc) 	Sets the status code for this response.
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            // Não manda nada no 'out' !
            log.avisoN("O arquivo solicitado não existe !"); // Mostra no console -- para testes de requisição
            log.avisoN("Arquivo solicitado: "+pathTranslated);
        }
        else {
            response.setStatus(HttpServletResponse.SC_OK);
            //Stream<String> BufferedReader.lines() Returns a Stream, the elements of which are lines read from this BufferedReader.
            Stream<String> stream = in.lines();

            //Object[] InterfaceStream.toArray() Returns an array containing the elements of this stream.
            //String[] strings = stream.toArray(); //incompatible types: java.lang.Object[] cannot be converted to java.lang.String[]
            //Object<String>[] strings = stream.toArray(); //type java.lang.Object does not take parameters
            /*for(String s : strings){
                out.print(s+"\n");
            }*/
            Object[] objects = stream.toArray();
            for(Object o : objects){
                //public static String valueOf(Object obj)
                out.print(o.toString()+"\n"); // Mostra no browser
                log.debN(o.toString()); // Mostra no console -- para testes de requisição
            }
        }
    }
}
