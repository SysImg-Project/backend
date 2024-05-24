package com.gbl.app.endpoint;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

import com.gbl.Log; // Para obter 'log'
import com.gbl.DB; // Para obter 'conn'


//@WebServlet(name = "DropDatabase", urlPatterns = {"/manut/dropdatabase"}) // <--DESCOMENTAR AQUI PARA PODER EXISTIR ESSA ROTA NO SERVIDOR !!!
public class Prototipo extends HttpServlet {
    private Log log; // Instância do objeto Log -- para logs
    private DB db;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        this.log = Log.getInstance(); // Obtêm uma instância do objeto Log -- para logs

        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getOutputStream();

        this.db = DB.getInstance();

        String comando = "USE database sysimg_des;"; // comando DDL -- retorna um int
        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
            PreparedStatement updateStatement = conn.prepareStatement(comando);
            int rs = updateStatement.executeUpdate();
            out.print(comando+"\n");
            out.print("rs ="+rs+"\n");
        }
        catch (SQLException e) {
            log.erroN(e.toString()); // log normal (console e/ou arquivo)
            out.print(e.toString()); // Imrime no browser ?
        }

    }

}
